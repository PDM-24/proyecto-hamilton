import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.rostorga.calendariumv2.data.database.entities.TaskData
import com.rostorga.calendariumv2.viewModel.UserViewModel
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreenContainer(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Calendar") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                CalendarScreen(navController = navController)
            }
        },
        floatingActionButton = { CalendarFAB() },
        floatingActionButtonPosition = FabPosition.End
    )
}

@Composable
fun CalendarFAB() {
    FloatingActionButton(
        onClick = { /* Add your action here */ },
        containerColor = Color(0xFF6784FE),
        contentColor = Color(0xFFFFFFFF),
        shape = CircleShape
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add", modifier = Modifier.size(40.dp))
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(navController: NavController, userViewModel: UserViewModel = viewModel()) {
    val tasks by userViewModel.allTasks.observeAsState(initial = emptyList())
    val currentDate = LocalDate.now()

    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    // Calculate the date range to display
    val maxDate = tasks.maxOfOrNull { LocalDate.parse(it.Date, dateFormatter) } ?: currentDate
    val daysBetween = Duration.between(currentDate.atStartOfDay(), maxDate.atStartOfDay()).toDays().toInt()
    val weeksBetween = (daysBetween / 7) + 1

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        // Horizontal scrolling for weeks
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
                .padding(32.dp)  // Add padding to avoid overlap with the top bar
        ) {
            for (weekIndex in 0 until weeksBetween) {
                Column {
                    // days of the week
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.width(60.dp)) // Leave space for the hours
                        for (i in 0..6) {
                            val day = currentDate.plusDays((weekIndex * 7 + i).toLong())
                            Box(
                                modifier = Modifier
                                    .width(100.dp)
                                    .padding(12.dp)
                                    .background(Color.LightGray)
                            ) {
                                Text(
                                    text = day.format(dateFormatter),
                                    modifier = Modifier.align(Alignment.Center),
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    // Main grid with hours and tasks
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                    ) {
                        // Hours column
                        Column(
                            modifier = Modifier
                                .width(80.dp)
                                .padding(8.dp)
                        ) {
                            for (hour in 0..23) {
                                Box(
                                    modifier = Modifier
                                        .height(70.dp) // Adjust height as needed
                                        .padding(4.dp)
                                ) {
                                    Text(
                                        text = String.format("%02d:00", hour),
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }
                        }

                        // Days and tasks grid
                        for (i in 0..6) {
                            val day = currentDate.plusDays((weekIndex * 7 + i).toLong())
                            Column(
                                modifier = Modifier
                                    .width(100.dp)
                                    .padding(8.dp)
                            ) {
                                for (hour in 0..23) {
                                    val tasksForHour = tasks.filter { task ->
                                        val taskDate = LocalDate.parse(task.Date, dateFormatter)
                                        val taskStartHour = LocalTime.parse(task.TimeStart, timeFormatter).hour
                                        val taskEndHour = LocalTime.parse(task.TimeFinish, timeFormatter).hour
                                        taskDate == day && (taskStartHour <= hour && taskEndHour >= hour)
                                    }

                                    Box(
                                        modifier = Modifier
                                            .height(80.dp) // Adjust height as needed
                                            .background(Color.LightGray) // Adjust background color
                                    ) {
                                        tasksForHour.forEach { task ->
                                            TaskCard(task = task)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskCard(task: TaskData) {
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val startTime = LocalTime.parse(task.TimeStart, timeFormatter)
    val endTime = LocalTime.parse(task.TimeFinish, timeFormatter)
    val durationMinutes = Duration.between(startTime, endTime).toMinutes().toInt()
    val durationHours = durationMinutes / 60 + if (durationMinutes % 60 > 0) 1 else 0

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height((durationHours * 60).dp) // Adjust height based on duration
            .padding(2.dp)
            .background(Color.Cyan) // Adjust color
    ) {
        Column {
            Text(text = task.TaskName, fontWeight = FontWeight.Bold)
            Text(text = "${task.TimeStart} - ${task.TimeFinish}")
            Text(text = task.TaskDesc)
        }
    }
}

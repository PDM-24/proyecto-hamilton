import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
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
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.delay

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
    val highlightedSlots = remember { mutableStateOf<List<Pair<LocalDate, LocalTime>>>(emptyList()) }
    var highlightOption by remember { mutableStateOf(0) }

    LaunchedEffect(highlightOption) {
        if (highlightOption != 0) {
            highlightedSlots.value = highlightFreeSlots(tasks, highlightOption)
            delay(3000) // Adjust delay as needed (e.g., 3000 milliseconds = 3 seconds)
            highlightedSlots.value = emptyList()
            highlightOption = 0
        }
    }

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
                .padding(top = 64.dp)  // Add padding to avoid overlap with the top bar
        ) {
            for (weekIndex in 0 until weeksBetween) {
                Column {
                    // Days of the week
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
                                        .padding(2.dp)
                                ) {
                                    Text(
                                        text = String.format("%02d:00", hour),
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }
                        }

                        // Days and tasks grid
                        for (i in 0..7) {
                            val day = currentDate.plusDays((weekIndex * 7 + i).toLong())
                            Column(
                                modifier = Modifier
                                    .width(100.dp)
                                    .padding(2.dp)
                            ) {
                                for (hour in 0..23) {
                                    val tasksForHour = tasks.filter { task ->
                                        val taskDate = LocalDate.parse(task.Date, dateFormatter)
                                        val taskStartHour = LocalTime.parse(task.TimeStart, timeFormatter).hour
                                        val taskEndHour = LocalTime.parse(task.TimeFinish, timeFormatter).hour
                                        taskDate == day && (taskStartHour <= hour && taskEndHour >= hour)
                                    }

                                    val isFreeSlot = highlightedSlots.value.any { it.first == day && it.second.hour == hour }

                                    Box(
                                        modifier = Modifier
                                            .height(90.dp)
                                            .width(100.dp)
                                            .background(if (isFreeSlot) Color(0xFFf8ed77) else Color.Transparent)
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = { highlightOption = 1 }) {
            Text("Free Slots 1")
        }
        Button(onClick = { highlightOption = 2 }) {
            Text("Free Slots 2")
        }
        Button(onClick = { highlightOption = 3 }) {
            Text("Free Slots 3")
        }
    }


}

@RequiresApi(Build.VERSION_CODES.O)
fun highlightFreeSlots(tasks: List<TaskData>, highlightOption: Int): List<Pair<LocalDate, LocalTime>> {
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    val freeSlots = mutableListOf<Pair<LocalDate, LocalTime>>()

    // Determine the date range to check for free slots
    val currentDate = LocalDate.now()
    val maxDate = tasks.maxOfOrNull { LocalDate.parse(it.Date, dateFormatter) } ?: currentDate
    val daysBetween = Duration.between(currentDate.atStartOfDay(), maxDate.atStartOfDay()).toDays().toInt()

    for (dayOffset in 0..daysBetween) {
        val day = currentDate.plusDays(dayOffset.toLong())

        for (hour in 0..23) {
            val slotStartTime = LocalTime.of(hour, 0)
            val slotEndTime = LocalTime.of(hour, 59)

            val tasksForHour = tasks.filter { task ->
                val taskDate = LocalDate.parse(task.Date, dateFormatter)
                val taskStartTime = LocalTime.parse(task.TimeStart, timeFormatter)
                val taskEndTime = LocalTime.parse(task.TimeFinish, timeFormatter)
                taskDate == day && (taskStartTime.isBefore(slotEndTime) && taskEndTime.isAfter(slotStartTime))
            }

            if (tasksForHour.isEmpty()) {
                freeSlots.add(Pair(day, slotStartTime))
            }
        }
    }

    return freeSlots
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
            .background(color = Color(0xFFeab676)) // Adjust color
    ) {
        Column {
            Text(text = task.TaskName, fontWeight = FontWeight.Bold)
            Text(text = "${task.TimeStart} - ${task.TimeFinish}")
            Text(text = task.TaskDesc)
        }
    }
}

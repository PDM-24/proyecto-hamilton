import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import android.widget.CalendarView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import com.rostorga.calendariumv2.R
import com.rostorga.calendariumv2.screens.profileScreen
import com.rostorga.calendariumv2.screens.CreateOrJoinTeam
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rostorga.calendariumv2.viewModel.UserViewModel
import com.rostorga.calendariumv2.data.database.entities.TaskData
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.rostorga.calendariumv2.viewModel.ApiViewModel

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ViewContainer(navController: NavController, apiViewModel: ApiViewModel) {
    Scaffold(
        topBar = { ToolBar() },
        content = { HomeScreenContent(navController, userViewModel = UserViewModel(Application()) ,apiViewModel) },
        floatingActionButton = { FAB(navController) },
        floatingActionButtonPosition = FabPosition.End
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBar() {

    var showProfile by remember { mutableStateOf(false) }

    if (showProfile) {
        profileScreen(onDismiss = { showProfile = false })
    }

    TopAppBar(title = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = R.drawable.menuicon), contentDescription = null, modifier = Modifier.size(50.dp))
            Image(painter = painterResource(id = R.drawable.user), contentDescription = null, modifier = Modifier
                .size(36.dp)
                .clickable { showProfile = true })
        }
    })
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FAB(navController: NavController, userViewModel: UserViewModel = viewModel()) {
    var showDialog by remember { mutableStateOf(false) }
    var showCalendar by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }

    if (showCalendar) {
        CalendarDialogPopUp(
            onDismiss = { showCalendar = false },
            onDateSelected = { date ->
                selectedDate = date
                showCalendar = false
                showDialog = true
            }
        )
    }

    if (showDialog) {
        AddTaskPopUp(
            onDismiss = { showDialog = false },
            onNext = { showDialog = false },
            userViewModel = userViewModel,
            selectedDate = selectedDate,
            navController = navController
        )
    }

    FloatingActionButton(
        onClick = { showCalendar = true },
        containerColor = Color(0xFF6784FE),
        contentColor = Color(0xFFFFFFFF),
        shape = CircleShape
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add", modifier = Modifier.size(40.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskPopUp(
    onDismiss: () -> Unit,
    onNext: ()-> Unit,
    userViewModel: UserViewModel,
    selectedDate: String,
    navController: NavController
) {
    var task by remember { mutableStateOf("") }
    var taskDesc by remember { mutableStateOf("") }
    val timeState1 = rememberTimePickerState(9, 30, false)
    val timeState2 = rememberTimePickerState(13, 30, false)
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFFB8478))
                .padding(8.dp)
                .width(300.dp)
                .height(480.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("From:")
                TimeInput(state = timeState1, modifier = Modifier.height(75.dp))
                Text("To:")
                TimeInput(state = timeState2, modifier = Modifier.height(75.dp))

                Spacer(modifier = Modifier.padding(8.dp))
                OutlinedTextField(
                    value = task,
                    onValueChange = { task = it },
                    shape = RoundedCornerShape(24.dp),
                    placeholder = { Text(text = "Add a title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(56.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = taskDesc,
                    onValueChange = { taskDesc = it },
                    shape = RoundedCornerShape(24.dp),
                    placeholder = { Text(text = "Add a description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(100.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Selected Date: $selectedDate")

                Button(
                    onClick = {
                        val startTime = String.format("%02d:%02d", timeState1.hour, timeState1.minute)
                        val endTime = String.format("%02d:%02d", timeState2.hour, timeState2.minute)
                        val taskData = TaskData(
                            TaskName = task,
                            TaskDesc = taskDesc,
                            Date = selectedDate,
                            TimeStart = startTime,
                            TimeFinish = endTime,
                            PersonId = 1 // Replace with actual user ID
                        )

                        scope.launch {
                            userViewModel.addTask(taskData)
                            Toast.makeText(context, "Task added!", Toast.LENGTH_SHORT).show()
                        }

                        navController.navigate("calendar")

                        onDismiss()
                    },
                    modifier = Modifier
                        .height(40.dp)
                        .width(200.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFF0F0))
                ) {
                    Text("ADD TASK", color = Color.Black)
                }
            }
        }
    }
}




@Composable
fun CalendarDialogPopUp(
    onDismiss: () -> Unit,
    onDateSelected: (String) -> Unit
) {
    var date by remember { mutableStateOf("") }
    val context = LocalContext.current


    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                onDateSelected(date)
                onDismiss()
            }) {
                Text(text = "OK")
            }
        },
        text = {
            AndroidView(factory = { CalendarView(it) }, update = {
                it.setOnDateChangeListener { _, year, month, day ->
                    date = String.format("%02d/%02d/%04d", day, month + 1, year)
                }
            })
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(navController: NavController, userViewModel: UserViewModel = viewModel(), apiViewModel: ApiViewModel) {
    val tasks by userViewModel.allTasks.observeAsState(initial = emptyList())
    var date by remember { mutableStateOf("") }

    var showCreateTeam by remember { mutableStateOf(false) }

    if (showCreateTeam) {
        CreateOrJoinTeam(onDismiss = { showCreateTeam = false }, userViewModel=userViewModel, apiViewModel)
    }

    val stroke = Stroke(
        width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.End) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Box(
                    Modifier
                        .size(180.dp, 8.dp)
                        .background(Color(0xFFBA74A8), shape = RoundedCornerShape(15.dp)),
                    contentAlignment = Alignment.Center
                ) {}
            }
            Text(text = "Not a lot going on today!")
        }

        Spacer(modifier = Modifier.padding(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .size(180.dp, 60.dp)
                    .padding(8.dp)
                    .clickable { showCreateTeam = true }
                    .drawBehind {
                        drawRoundRect(
                            color = Color(0xFFBA74A8),
                            style = stroke,
                            cornerRadius = CornerRadius(10.dp.toPx())
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(textAlign = TextAlign.Center, text = "Create or join a team!")
            }
        }

        Box(
            modifier = Modifier
                .padding(16.dp)
                .shadow(8.dp, shape = RoundedCornerShape(8.dp))
                .background(Color.White, shape = RoundedCornerShape(8.dp))
        ) {
            AndroidView(
                factory = { CalendarView(it) },
                update = { it.setOnDateChangeListener { _, year, month, day -> date = "$day - ${month + 1} - $year" } }
            )
        }

        Text(text = date)

        Text(
            modifier = Modifier.padding(8.dp),
            text = "TASKS FOR TODAY",
            style = TextStyle(fontWeight = FontWeight.Light, fontSize = 18.sp)
        )
        Spacer(modifier = Modifier.padding(8.dp))

        LazyColumn {
            items(tasks) { task ->
                Box(
                    Modifier
                        .fillMaxWidth()
                        .clickable(onClick = { navController.navigate("calendar") })
                        .padding(8.dp)
                        .drawBehind {
                            drawRoundRect(
                                color = Color(0xFFFC6E60),
                                cornerRadius = CornerRadius(12.dp.toPx())
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Text(text = "Task: ${task.TaskName}", color= Color.White)
                        Text(text = "Description: ${task.TaskDesc}", color= Color.White)
                        Text(text = "From: ${task.TimeStart} To: ${task.TimeFinish}", color= Color.White)
                        Text(text = "Date: ${task.Date}", color= Color.White)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.padding(8.dp))
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun AddTaskPopUpPreview() {
    AddTaskPopUp(onDismiss = {}, onNext = {}, userViewModel = UserViewModel(application = Application()), selectedDate = "01 - 01 - 2023", navController = NavController(LocalContext.current))
}

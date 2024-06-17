import android.annotation.SuppressLint
import android.os.Build
import android.widget.CalendarView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.maxkeppeler.sheets.calendar.CalendarView
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import java.time.LocalDate
import com.rostorga.calendariumv2.screens.profileScreen
import com.rostorga.calendariumv2.screens.CreateOrJoinTeam
import com.rostorga.calendariumv2.screens.CreateTeam

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun ViewContainer() {
    Scaffold(
        topBar = { ToolBar() },
        content = { HomeScreenContent() },
        floatingActionButton = { FAB() },
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
            Image(painter = painterResource(id = R.drawable.user), contentDescription = null, modifier = Modifier.size(36.dp)
                .clickable { showProfile = true })
        }
    })
}

@Composable
fun FAB() {
    var showDialog by remember { mutableStateOf(false) }
    var showCalendar by remember { mutableStateOf(false) }


    if (showDialog) {
        AddTaskPopUp(
            onDismiss = { showDialog = false },
            onNext = {
                showDialog = false
                showCalendar = true
            }
        )
    }
    if (showCalendar) {
        CalendarDialogPopUp(onDismiss = { showCalendar = false })
    }

    FloatingActionButton(
        onClick = { showDialog = true },
        containerColor = Color(0xFF6784FE),
        contentColor = Color(0xFFFFFFFF),
        shape = CircleShape
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add", modifier=Modifier.size(40.dp))
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskPopUp(
    onDismiss: () -> Unit,
    onNext: () -> Unit
) {
    var task by remember { mutableStateOf("") }
    val timeState1 = rememberTimePickerState(9, 15, false)
    val timeState2 = rememberTimePickerState(9, 15, false)

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFFB8478))
                .padding(8.dp)
                .width(300.dp)
                .height(500.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Add New Task",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text="From:"
                )
                TimeInput(state = timeState1, modifier=Modifier.height(80.dp))
                Text(
                    text="To:"
                )
                TimeInput(state = timeState2, modifier=Modifier.height(80.dp))

                Spacer(modifier = Modifier.padding(16.dp))
                OutlinedTextField(
                    value = task,
                    onValueChange = { task = it },
                    shape = RoundedCornerShape(12.dp),
                    placeholder = { Text(text = "Add a description :) ") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(100.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onNext,
                    modifier = Modifier
                        .height(40.dp)
                        .width(200.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFF0F0))
                ) {
                    Text("NEXT", color=Color.Black)
                }

            }
        }
    }
}

@Composable
fun CalendarDialogPopUp(
    onDismiss:()-> Unit
){
    var date by remember { mutableStateOf(" ")}
    AlertDialog(onDismissRequest = onDismiss , confirmButton = {
        //center the button but yeah mostly works now :D
            Button(onClick=onDismiss ){
                Text(text="OK")
    }

    },
        text={
            AndroidView(factory = { CalendarView(it)}, update = { it.setOnDateChangeListener{ _, year, month, day -> date = "$day - ${month + 1} - $year" }}
            )
        }
    )

}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent() {
    var date by remember { mutableStateOf(" ") }

    var showCreateTeam by remember { mutableStateOf(false) }

    if (showCreateTeam) {
        CreateOrJoinTeam(onDismiss = { showCreateTeam = false })
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

// this one should be on the right and should be thinner xd but i dont care right now


        Column(modifier=Modifier.padding(12.dp), horizontalAlignment = Alignment.End) {
            Row(modifier = Modifier
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End){
                Box(
                    Modifier
                        .size(180.dp, 8.dp)
                        .background(Color(0xFFBA74A8), shape = RoundedCornerShape(15.dp)),
                    contentAlignment = Alignment.Center
                ) {
                }
            }
            Text(text="not a lot going on today!")


        }


        Spacer(modifier=Modifier.padding(4.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
//this box has to be clickable
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

        Box(
            Modifier
                .size(350.dp, 60.dp)
                .drawBehind {
                    drawRoundRect(
                        color = Color.Red,
                        style = stroke,
                        cornerRadius = CornerRadius(8.dp.toPx())
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Text(textAlign = TextAlign.Center, text = "Add a new task")
        }

        Spacer(modifier = Modifier.padding(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun AddTaskPopUpPreview() {
    AddTaskPopUp(onDismiss = {}, onNext={})
}


import android.annotation.SuppressLint
import android.os.Build
import android.widget.CalendarView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.rostorga.calendariumv2.R
import com.maxkeppeler.sheets.calendar.CalendarView
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import java.time.LocalDate

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
    TopAppBar(title = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = R.drawable.menuicon), contentDescription = null, modifier = Modifier.size(36.dp))
            Image(painter = painterResource(id = R.drawable.user), contentDescription = null, modifier = Modifier.size(36.dp))
        }
    })
}

@Composable
fun FAB() {

    val context = LocalContext.current
    FloatingActionButton(
        onClick = {
            Toast.makeText(context, "Button clicked", Toast.LENGTH_SHORT).show()
        },
        shape = CircleShape
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent() {
    var date by remember {
        mutableStateOf(" ")
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


        Text("TODO CREATE THE LITTLE BAR THAT TRACKS THE WORKLOAD OF THE FIGMA")
        Text("TODO JOIN OR CREATE A TEAM LIKE IN THE FIGMA")

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
            Text(textAlign = TextAlign.Center, text = "Tap here to introduce yourself")
        }

        Spacer(modifier = Modifier.padding(8.dp))
    }
}

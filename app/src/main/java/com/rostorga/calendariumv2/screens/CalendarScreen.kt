import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.rostorga.calendariumv2.data.database.entities.TaskData
import com.rostorga.calendariumv2.viewModel.UserViewModel


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreenContainer(navController: NavHostController) {
    Scaffold(
        topBar={
               TopAppBar(title = {
                   Text(text="Calendar")
               },
                   navigationIcon = { IconButton(onClick = {navController.popBackStack() }) {
                       Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                   }}
                   )
        },
        content = {
            CalendarScreen(navController = navController)
        },
        floatingActionButton = { CalendarFAB() },
        floatingActionButtonPosition = FabPosition.End
    )
}





@Composable
fun CalendarFAB() {
    FloatingActionButton(
        onClick = {  },
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


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .background(color = Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(800.dp)
                .horizontalScroll(rememberScrollState())
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                modifier = Modifier.width(1000.dp)
            ) {
                items(720) { index ->
                    val task =
                        tasks.getOrNull(index)  // Assuming index corresponds to task position
                    if (task != null) {
                        TaskCard(task)
                    } else {
                        EmptyCard(index)
                    }
                }
            }

            // You can add more content here if needed
        }
    }


}

    @Composable
    fun TaskCard(task: TaskData) {
        Card(
            modifier = Modifier
                .size(50.dp)
                .padding(2.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0))
        ) {
            Box(contentAlignment = Alignment.Center) {
                Column {
                    Text(text = task.TaskName)
                    Text(text = task.TimeStart)
                }
            }
        }
    }


    @Composable
    fun EmptyCard(index: Int) {
        Card(
            modifier = Modifier
                .size(50.dp)
                .padding(2.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0))
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(text = index.toString())
            }
        }
    }

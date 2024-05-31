package com.rostorga.calendariumv2.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rostorga.calendariumv2.R


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun ViewContainer(){
    Scaffold(
        topBar={ ToolBar()},
        content= {HomeScreenContent()},
        floatingActionButton= { FAB()},
        floatingActionButtonPosition = FabPosition.End

    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBar(){

    TopAppBar(title={
        Row(modifier= Modifier
            .fillMaxWidth()
            .padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically){
            Image(painter= painterResource(id = R.drawable.menuicon), contentDescription = null, modifier= Modifier.size(36.dp) )
            Image(painter= painterResource(id = R.drawable.user), contentDescription = null, modifier= Modifier.size(36.dp) )


        }

    })
}

@Composable
fun FAB() {
    val context = LocalContext.current
    FloatingActionButton(onClick = {
        Toast.makeText(context,"nice cock", Toast.LENGTH_SHORT).show()
    },shape= CircleShape){
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
    }

}



@Composable
fun HomeScreenContent(){
    LazyColumn(
        modifier= Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.Red)) {



    }


}

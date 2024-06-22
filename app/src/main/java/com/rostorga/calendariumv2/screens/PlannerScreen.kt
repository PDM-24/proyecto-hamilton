package com.rostorga.calendariumv2.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rostorga.calendariumv2.DataModel.TimeBlockDataObject
import com.rostorga.calendariumv2.DataModel.TimeBlockType
import com.rostorga.calendariumv2.DataModel.getEmptyDay

enum class DayOfWeek{
    Mon, Tue, Wed, Thr, Fri, Sat, Sun
}

val montLength = 31
val firstDate = 1
@Composable
fun PlannerScreen(navController: NavController) {
    val col1 = getEmptyDay()
    col1[2] = TimeBlockDataObject(color = Color.Gray, text = "Help", type = TimeBlockType.TOP)
    col1[3] = TimeBlockDataObject(color = Color.Gray, type = TimeBlockType.MIDDLE)
    col1[4] = TimeBlockDataObject(color = Color.Gray, type = TimeBlockType.BOTTOM)

    var month: MutableList<MutableList<TimeBlockDataObject>> = mutableListOf(mutableListOf(TimeBlockDataObject()))

    month.add(col1)

    for( i in 1..30){
        month.add(getEmptyDay())
    }

    Box(
        modifier = Modifier
            .border(1.dp, Color.Blue)
    ){
        LazyRow {
            items(month) {item ->
                dayColumn(data = item)
            }
        }
    }
}

@Composable
fun TimeBlock(data: TimeBlockDataObject) {

    var mod:Modifier = Modifier
        .background(data.color)
        .height(30.dp)
        .width(40.dp)

    when(data.type){
        TimeBlockType.TOP -> mod = mod.clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
        TimeBlockType.BOTTOM -> mod = mod.clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
        TimeBlockType.ALONE -> mod = mod.clip(RoundedCornerShape(10.dp))
        TimeBlockType.MIDDLE -> 0
    }
    
    Box(
        modifier = mod
    ){
        Text(text = data.text)
    }
}

@Composable
fun dayColumn(data: List<TimeBlockDataObject>) {
    Column(
        modifier = Modifier
            .fillMaxHeight(0.5f)
    ) {
        for(item in data) {
            if(!item.isEmpty){
                Spacer(modifier = Modifier.height(30.dp))
            } else{
                item.text = "0"
                TimeBlock(data = item)
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}
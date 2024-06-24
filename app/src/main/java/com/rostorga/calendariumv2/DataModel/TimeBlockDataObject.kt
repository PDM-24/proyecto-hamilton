package com.rostorga.calendariumv2.DataModel

import androidx.compose.ui.graphics.Color

enum class TimeBlockType{
    TOP, MIDDLE, BOTTOM, ALONE
}

data class TimeBlockDataObject(
    var isEmpty: Boolean = true,
    var color: Color = Color.Transparent,
    var type: TimeBlockType = TimeBlockType.ALONE,
    var text: String = ""
)

//Returns a list of 24 TimeBlockDataObjects
fun getEmptyDay(): MutableList<TimeBlockDataObject> {
    val temp = mutableListOf<TimeBlockDataObject>()
    for(i in 1..24){temp.add(TimeBlockDataObject())}

    return temp
}

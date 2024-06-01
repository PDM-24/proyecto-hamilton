package com.rostorga.calendariumv2.DataModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector


val TaskList: MutableState<MutableList<DataModel>> = mutableStateOf(mutableListOf( ))




data class DataModel(
    val title: String,
    val description: String,
    val deadline: String,
    val icon: ImageVector,
    var doneState: Boolean,
    val Category: String

)
package com.rostorga.calendariumv2.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


object num {
    var Numero = mutableStateOf(0)

    fun setNum(index: Int) {
        Numero.value = index

    }
}

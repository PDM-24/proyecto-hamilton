package com.rostorga.calendariumv2.screens

import androidx.compose.runtime.mutableStateOf


object num {
    var Numero = mutableStateOf(1)

    fun setNum(index: Int) {
        Numero.value = index
    }
}
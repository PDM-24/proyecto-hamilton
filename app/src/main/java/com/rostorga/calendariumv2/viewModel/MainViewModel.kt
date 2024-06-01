package com.rostorga.calendariumv2.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){

    var isDialogShown by mutableStateOf(false)
        private set

    fun onBuyClikc(){
        isDialogShown=true
    }
    fun onDismissDialog(){
        isDialogShown=false
    }



}
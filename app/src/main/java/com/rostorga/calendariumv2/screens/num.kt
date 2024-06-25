package com.rostorga.calendariumv2.screens

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


object num {
    private const val PREF_NAME = "MyGlobalPreferences"
    private const val KEY_GLOBAL_VALUE = "global_value"
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    var numero: Int
        get() = sharedPreferences.getInt(KEY_GLOBAL_VALUE, 0)
        set(value) = sharedPreferences.edit().putInt(KEY_GLOBAL_VALUE, value).apply()

    var nn = mutableStateOf(0)
    fun setNum(index: Int) {
        nn.value = index

    }


}


/*
object num {
    var Numero = mutableStateOf(0)

    fun setNum(index: Int) {
        Numero.value = index

    }
}

 */

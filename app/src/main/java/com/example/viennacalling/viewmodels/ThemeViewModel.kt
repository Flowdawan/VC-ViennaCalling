package com.example.viennacalling.viewmodels

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ThemeViewModel: ViewModel() {
        private val _darkMode = mutableStateOf(true)
        val darkMode: State<Boolean> = _darkMode

        fun onThemeChanged(newTheme: String) {
            when (newTheme) {
                "Light" -> _darkMode.value = false
                "Dark" -> _darkMode.value = true
            }
        }
}
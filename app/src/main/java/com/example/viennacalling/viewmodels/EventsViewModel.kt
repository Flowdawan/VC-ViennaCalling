package com.example.viennacalling.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viennacalling.models.Event
import com.example.viennacalling.models.Goa
import com.example.viennacalling.retrofit.APIService
import kotlinx.coroutines.launch

class EventsViewModel : ViewModel() {
    private val _eventList = mutableStateListOf<Goa>()
    var errorMessage: String by mutableStateOf("")
    val eventList: List<Goa>
        get() = _eventList

    fun getEventList() {
        viewModelScope.launch {
            val apiService = APIService.getInstance()
            try {
                _eventList.clear()
                _eventList.addAll(apiService.getEvents())

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}

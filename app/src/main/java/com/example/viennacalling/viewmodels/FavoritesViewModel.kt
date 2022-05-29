package com.example.viennacalling.viewmodels

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viennacalling.models.Event
import com.example.viennacalling.models.getEvents
import com.example.viennacalling.repository.EventsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val repository: EventsRepository
) : ViewModel() {

    private val _favoriteEvents = MutableStateFlow<List<Event>>(emptyList())
    val favoriteEvents = _favoriteEvents.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllEvents().collect { eventList ->
                _favoriteEvents.value = eventList
            }
        }
    }

    fun addEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            if(!isEventInList(event)){
                repository.addEvent(event = event)
            }
        }
    }

    fun removeEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
                repository.deleteEvent(event)
        }
    }

    fun editEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.editEvent(event = event)
        }
    }

    fun isEventInList(event: Event): Boolean {
        var eventList = favoriteEvents.value
        var isInList = false
        eventList.forEach() {
            isInList = it.id == event.id
        }
        return isInList
    }

    /* fun getEventByName(title: String): Event {
         viewModelScope.launch(Dispatchers.IO) {
             return repository.getEventByName(title = title)
         }
     }

     suspend fun getEventById(id: Long): Event {
         viewModelScope.launch(Dispatchers.IO) {
             return repository.getEventById(id = id)
         }
     }*/
}


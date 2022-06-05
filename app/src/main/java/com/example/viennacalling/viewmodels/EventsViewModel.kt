package com.example.viennacalling.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viennacalling.models.Event
import com.example.viennacalling.repository.EventsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventsViewModel(
    private val repository: EventsRepository
) : ViewModel() {

    private val _eventList = mutableStateListOf<Event>()

    val eventList: List<Event>
        get() = _eventList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchEventsRssFeed(eventList = _eventList)
        }
    }

    fun getAllEvents(): List<Event> {
        return _eventList
    }
}

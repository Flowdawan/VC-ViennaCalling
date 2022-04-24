package com.example.viennacalling.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.viennacalling.models.Event


class FavoritesViewModel: ViewModel() {
    private val _favoriteEvents = mutableStateListOf<Event>()

    val favoriteEvents: List<Event>
        get() = _favoriteEvents

    fun addEvent(event: Event) {
        if(!isEventInList(event)){
            _favoriteEvents.add(event)
        }
    }

    fun removeEvent(event: Event) {
        _favoriteEvents.remove(event)
    }

    fun getAllEvents(): List<Event> {
        return _favoriteEvents
    }

    fun isEventInList(event: Event): Boolean {
        return _favoriteEvents.contains(event)
    }

    fun getOneEvent(event: Event): Event? {
        return _favoriteEvents.find { it.id == event.id }
    }
}


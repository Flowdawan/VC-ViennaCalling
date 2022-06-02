package com.example.viennacalling.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viennacalling.models.Event
import com.example.viennacalling.repository.EventsRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val repository: EventsRepository
) : ViewModel() {

    private val _favoriteEvents = MutableStateFlow<List<Event>>(emptyList())
    val favoriteEvents = _favoriteEvents.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            if (Firebase.auth.currentUser != null) {
                repository.getFirebaseEvents(_favoriteEvents)
            } else {
                repository.getAllEvents().collect { eventList ->
                    _favoriteEvents.value = eventList
                }
            }
        }
    }

    fun addEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!isEventInList(event)) {
                repository.addEvent(event = event)
                if (Firebase.auth.currentUser != null) {
                    repository.addFirebaseEvent(event = event)
                }
            }
        }
    }

    fun removeEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteEvent(event.title)
            if (Firebase.auth.currentUser != null) {
                repository.deleteFirebaseEvent(event)
            }
        }
    }

    fun editEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.editEvent(event = event)
        }
    }

    fun isEventInList(event: Event): Boolean {
        return favoriteEvents.value.contains(event)
    }
}


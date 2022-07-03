package at.deflow.viennacalling.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.deflow.viennacalling.models.Event
import at.deflow.viennacalling.repository.EventsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "FavoritesViewModel"

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
            if (!isEventInList(event)) {
                repository.addEvent(event = event)
            }
        }
    }

    fun removeEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isEventInList(event)) {
                repository.deleteEvent(event)
            }
        }
    }

    fun isEventInList(event: Event): Boolean {
        var isInList = false
        _favoriteEvents.value.forEach { eventItem ->
            if(event.id == eventItem.id){
                isInList = true
            }
        }
        return isInList
    }
}


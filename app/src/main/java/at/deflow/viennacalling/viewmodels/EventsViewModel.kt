package at.deflow.viennacalling.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.deflow.viennacalling.models.Event
import at.deflow.viennacalling.repository.EventsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "EventsViewModel"

class EventsViewModel(
    private val repository: EventsRepository
) : ViewModel() {

    private val _eventList = mutableStateListOf<Event>()

    val eventList: List<Event>
        get() = _eventList

    private val _filteredList = mutableStateListOf<Event>()

    val filteredList: List<Event>
        get() = _filteredList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchEventsRssFeed(
                eventList = _eventList,
                categoryId = "6,+68,+90,+64,+91,+73",
                subCategory = "6,+68,+73"
            )
        }
    }

    fun getAllEvents(): List<Event> {
        return _eventList
    }

    fun getAllFilteredEvents(): List<Event> {
        return _filteredList
    }

    fun filterEventsByCategory(categoryId: String = "6", subCategory: String = ""): List<Event> {
        _filteredList.clear()
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchEventsRssFeed(
                eventList = _filteredList,
                categoryId = categoryId,
                subCategory = subCategory
            )
        }
        return _filteredList
    }
}

package at.deflow.viennacalling.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
    var eventsFilterState by mutableStateOf(FilteredState())
        private set

    private val _eventList = mutableStateListOf<Event>()
    private var eventListSearch = mutableStateListOf<Event>()

    val eventList: List<Event>
        get() = _eventList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchEventsRssFeed(
                eventList = _eventList,
            )
        }
    }

    fun cacheNewList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchEventsRssFeed(
                eventList = eventListSearch,
            )
        }
    }

    fun getEventListForSearch(): List<Event> {
        return eventListSearch
    }


    fun fetchAllEventsNew(): List<Event> {
        _eventList.clear()
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchEventsRssFeed(
                eventList = _eventList,
            )
        }
        return _eventList
    }

    fun getAllEvents(): List<Event> {
        return _eventList
    }

    fun filterEventsByCategory(categoryId: String = "6", subCategory: String = ""): List<Event> {
        _eventList.clear()
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchEventsRssFeed(
                eventList = _eventList,
            )
        }
        return _eventList
    }
}

data class FilteredState(
    var appliedFilter: Int = 0,
    var clickedAdditionalInfo: Int = 0
)

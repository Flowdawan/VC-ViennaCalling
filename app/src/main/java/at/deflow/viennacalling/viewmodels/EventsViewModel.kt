package at.deflow.viennacalling.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
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
    private val eventListInitial = ArrayList<Event>()

    private var eventListSearch = mutableStateListOf<Event>()

    val eventList: List<Event>
        get() = _eventList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchEventsRssFeed(
                eventList = _eventList,
                eventListInitial = eventListInitial,
                )
        }
    }

    fun cacheNewList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchEventsRssFeed(
                eventList = eventListSearch,
                eventListInitial = eventListInitial,
            )
        }
    }

    fun getEventListForSearch(): List<Event> {
        return eventListSearch
    }


    fun fetchAllEventsNew(): List<Event> {
        _eventList.clear()
        eventListInitial.clear()

        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchEventsRssFeed(
                eventList = _eventList,
                eventListInitial = eventListInitial,
            )
        }
        return _eventList
    }

    fun getAllEvents(): List<Event> {
        return _eventList
    }

    fun removeAllFilter(): List<Event> {
        _eventList.clear()
        _eventList.addAll(eventListInitial)
        return _eventList
    }

    fun filterByParty(): List<Event> {
        _eventList.clear()
        _eventList.addAll(eventListInitial.filter { event ->
            event.category == "party"
        })
        return _eventList
    }

    fun filterBySightseeing(): List<Event> {
        _eventList.clear()
        _eventList.addAll(eventListInitial.filter { event ->
            event.category == "sightseeing"
        })
        return _eventList
    }

    fun filterByCulture(): List<Event> {
        _eventList.clear()
        _eventList.addAll(eventListInitial.filter { event ->
            event.category == "culture"
        })
        return _eventList
    }
}

data class FilteredState(
    var appliedFilter: Int = 0,
    var clickedAdditionalInfo: Int = 0
)

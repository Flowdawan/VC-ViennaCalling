package at.deflow.viennacalling.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.deflow.viennacalling.models.Event
import at.deflow.viennacalling.repository.EventsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import retrofit2.HttpException

class EventsViewModel(
    private val repository: EventsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EventsUiState())
    val uiState: StateFlow<EventsUiState> = _uiState.asStateFlow()

    private var eventListInitial = listOf<Event>()
    private val eventDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMAN)


    init {
        refreshEvents()
    }

    fun setCategoryFilter(category: Int) {
        val filteredEvents = when (category) {
            1 -> eventListInitial.filter { it.category == "sightseeing" }
            2 -> eventListInitial.filter { it.category == "culture" }
            3 -> eventListInitial.filter { it.category == "party" }
            else -> eventListInitial
        }
        _uiState.update { it.copy(events = filteredEvents, categoryFilter = category, dateFilter = 0) }
    }

    fun setDateFilter(dateFilter: Int) {
        val today = LocalDate.now()
        val filteredEvents = when (dateFilter) {
            1 -> eventListInitial.filter { event ->
                if (event.startTime.isBlank()) return@filter false
                runCatching {
                    val eventDate = LocalDate.parse(event.startTime, eventDateFormatter)
                    !eventDate.isBefore(today)
                }.getOrDefault(false)
            }
            2 -> eventListInitial.filter { event ->
                if (event.startTime.isBlank()) return@filter false
                runCatching {
                    LocalDate.parse(event.startTime, eventDateFormatter).isEqual(today)
                }.getOrDefault(false)
            }
            else -> eventListInitial
        }
        _uiState.update { it.copy(events = filteredEvents, dateFilter = dateFilter, categoryFilter = 0) }
    }

    fun retry() = refreshEvents(clearBefore = true)

    private fun refreshEvents(clearBefore: Boolean = false) {
        viewModelScope.launch {
            if (clearBefore) {
                _uiState.update { it.copy(events = emptyList()) }
            }
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val events = repository.fetchEvents()
                eventListInitial = events
                _uiState.update { it.copy(events = events, dateFilter = 0, categoryFilter = 0) }
            } catch (e: HttpException) {
                val errorBody = try { e.response()?.errorBody()?.string() } catch (e: Exception) { "Could not read error body." }
                _uiState.update { it.copy(errorMessage = "Fehler: HTTP ${e.code()} ${e.message()}. Body: $errorBody") }
            } catch (e: IOException) {
                _uiState.update { it.copy(errorMessage = "Netzwerkfehler. Bitte Internetverbindung überprüfen.") }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Unbekannter Fehler: ${e.message}") }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}

data class EventsUiState(
    val events: List<Event> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val categoryFilter: Int = 0, // 0: Home, 1: Attractions, 2: Culture, 3: Party
    val dateFilter: Int = 0, // 0: All, 1: From today, 2: Just today
)

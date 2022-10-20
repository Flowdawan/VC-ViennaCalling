package at.deflow.viennacalling.repository

import at.deflow.viennacalling.dao.EventsDao
import at.deflow.viennacalling.models.Event
import kotlinx.coroutines.flow.Flow

class EventsRepository(
    private val eventsDao: EventsDao,
) {

    // We can use the '=' (Single-expression functions) for the function or the bracket braces
    suspend fun addEvent(event: Event) = eventsDao.addEvent(event = event)

    fun getAllEvents(): Flow<List<Event>> = eventsDao.getAllEvents()

    suspend fun deleteEvent(event: Event) = eventsDao.deleteEvent(event = event)

    fun fetchEventsRssFeed(eventList: MutableList<Event>) = eventsDao.fetchEventRssFeed(
        eventList = eventList
    )
}


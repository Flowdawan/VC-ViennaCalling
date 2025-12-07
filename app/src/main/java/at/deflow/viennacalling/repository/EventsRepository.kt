package at.deflow.viennacalling.repository

import at.deflow.viennacalling.dao.EventsDao
import at.deflow.viennacalling.mapping.toEventList
import at.deflow.viennacalling.models.Event
import at.deflow.viennacalling.retrofit.RetrofitInstance
import kotlinx.coroutines.flow.Flow

class EventsRepository(
    private val eventsDao: EventsDao,
) {
    suspend fun addEvent(event: Event) = eventsDao.addEvent(event = event)

    fun getAllEvents(): Flow<List<Event>> = eventsDao.getAllEvents()

    suspend fun deleteEvent(event: Event) = eventsDao.deleteEvent(event = event)

    suspend fun fetchEvents(): List<Event> {
        return RetrofitInstance.api.getEventListAll().toEventList()
    }
}

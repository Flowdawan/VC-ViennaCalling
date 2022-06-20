package com.example.viennacalling.repository

import com.example.viennacalling.dao.EventsDao
import com.example.viennacalling.models.Event
import kotlinx.coroutines.flow.Flow

class EventsRepository(
    private val eventsDao: EventsDao,
) {

    // We can use the '=' (Single-expression functions) for the function or the bracket braces
    suspend fun addEvent(event: Event) = eventsDao.addEvent(event = event)

    fun getAllEvents(): Flow<List<Event>> = eventsDao.getAllEvents()

    suspend fun editEvent(event: Event) = eventsDao.editEvent(event = event)

    suspend fun deleteEvent(event: Event) = eventsDao.deleteEvent(event = event)

    suspend fun getEventByName(title: String): Event {
        return eventsDao.getEventByName(title = title)
    }

    suspend fun getEventById(id: Long): Event {
        return eventsDao.getEventById(id = id)
    }


    fun fetchEventsRssFeed(
        eventList: MutableList<Event>,
        categoryId: String = "6",
        subCategory: String = ""
    ) = eventsDao.fetchEventRssFeed(
        eventList = eventList, categoryId = categoryId
    )
}


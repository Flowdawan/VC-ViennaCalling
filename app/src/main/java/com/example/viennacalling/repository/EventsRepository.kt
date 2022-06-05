package com.example.viennacalling.repository

import com.example.viennacalling.dao.EventsDao
import com.example.viennacalling.dao.FirebaseDao
import com.example.viennacalling.models.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class EventsRepository(
    private val eventsDao: EventsDao,
    private val firebaseDao: FirebaseDao,
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

    // Firebase
    suspend fun addFirebaseEvent(event: Event) = firebaseDao.addFirebaseEvent(event = event)
    suspend fun deleteFirebaseEvent(event: Event) = firebaseDao.deleteFirebaseEvent(event = event)
    suspend fun getFirebaseEvents(_favoriteEvents: MutableStateFlow<List<Event>>) =
        firebaseDao.getFirebaseEvents(_favoriteEvents = _favoriteEvents)

    fun fetchEventsRssFeed(
        eventList: MutableList<Event>,
        categoryId: String = "6",
        subCategory: String = ""
    ) = eventsDao.fetchEventRssFeed(
        eventList = eventList, categoryId = categoryId
    )
}


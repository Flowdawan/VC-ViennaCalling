package com.example.viennacalling.repository

import androidx.compose.runtime.snapshots.SnapshotStateList
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

    suspend fun deleteEvent(title: String) = eventsDao.deleteEvent(title = title)

    suspend fun getEventByName(title: String): Event {
        return eventsDao.getEventByName(title = title)
    }

    suspend fun getEventById(id: Long): Event {
        return eventsDao.getEventById(id = id)
    }

    // Firebase
    suspend fun addFirebaseEvent(event: Event) = firebaseDao.addFirebaseEvent(event = event)
    suspend fun deleteFirebaseEvent(event: Event) = firebaseDao.deleteFirebaseEvent(event = event)
    suspend fun getFirebaseEvents(_favoriteEvents: MutableStateFlow<List<Event>>) = firebaseDao.getFirebaseEvents(_favoriteEvents = _favoriteEvents)

    suspend fun fetchEventsRssFeed(eventList: MutableList<Event>) = eventsDao.fetchEventRssFeed(
        eventList = eventList)

}























/*
import android.util.Log
import com.example.viennacalling.models.Event
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventsRepository @Inject constructor(private val queryEventsByName: Query) {
    suspend fun getEventsFromFirestore(): DataOrException<List<Event>, Exception> {
        val dataOrException = DataOrException<List<Event>, Exception>()
        try {
            dataOrException.data = queryEventsByName.get().await().map { document ->
                document.toObject(Event::class.java)
            }
        } catch (e: FirebaseFirestoreException) {
            dataOrException.e = e
        }
        return dataOrException
    }

    fun addData(event: Event, db: FirebaseFirestore){
        val event = event to FirebaseAuth.getInstance().currentUser?.uid
        Log.d("FavoriteRepo", "DocumentSnapshot added with ID: ${event}")

        // Add a new document with a generated ID
        db.collection("events")
            .add(event)
            .addOnSuccessListener { documentReference ->
                Log.d("FavoriteRepo", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("FavoriteRepo", "Error adding document", e)
            }
    }


}*/

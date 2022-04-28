
package com.example.viennacalling.repository
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

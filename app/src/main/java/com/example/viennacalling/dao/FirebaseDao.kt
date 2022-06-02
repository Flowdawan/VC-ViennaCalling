package com.example.viennacalling.dao

import android.util.Log
import com.example.viennacalling.models.Event
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow

private val TAG = "FirebaseDao"

class FirebaseDao {
    val firebaseDb = Firebase.firestore

    // We are saving as documentPath a combination of the logged in user Id and the id from the event,
    // therefore we get a unique document for every user who saved an event

    // Firebase add a new Event
    suspend fun addFirebaseEvent(event: Event) {
        val userId = Firebase.auth.currentUser?.uid
        event.uuid = userId

        // Add a new document with a generated ID
        firebaseDb.collection("events")
            .document("${userId}-${event.id}")
            .set(event)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: $documentReference")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    // Firebase deleting a event from firebase with the id
    suspend fun deleteFirebaseEvent(event: Event) {
        val userId = Firebase.auth.currentUser?.uid
        // Delete Document
        firebaseDb.collection("events").document("${userId}-${event.id}")
            .delete()
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot deleted with ID: $documentReference")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error deleting document", e)
            }
    }

    // Firebase loading the favorites events from firebase
    suspend fun getFirebaseEvents(_favoriteEvents: MutableStateFlow<List<Event>>) {
        val userId = Firebase.auth.currentUser?.uid
        firebaseDb.collection("events")
            .whereEqualTo("uuid", userId)
            .get()
            .addOnSuccessListener { documents ->
                _favoriteEvents.value = documents.toObjects()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }
}
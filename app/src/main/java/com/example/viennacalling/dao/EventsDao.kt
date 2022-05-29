package com.example.viennacalling.dao

import androidx.room.*
import com.example.viennacalling.models.Event
import kotlinx.coroutines.flow.Flow

@Dao
interface EventsDao {

        @Query("SELECT * FROM events")
        fun getAllEvents(): Flow<List<Event>>

        @Update
        suspend fun editEvent(event: Event)

        @Delete
        suspend fun deleteEvent(event: Event)

        @Insert
        suspend fun addEvent(event: Event)

        @Query("SELECT * FROM events WHERE title = :title")
        suspend fun getEventByName(title: String): Event

        @Query("SELECT * FROM events WHERE id = :id")
        suspend fun getEventById(id: Long): Event

}
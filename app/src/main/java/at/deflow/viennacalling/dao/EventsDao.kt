package at.deflow.viennacalling.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import at.deflow.viennacalling.models.Event
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

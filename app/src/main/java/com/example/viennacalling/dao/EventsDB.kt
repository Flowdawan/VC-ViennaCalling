package com.example.viennacalling.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.viennacalling.models.Event


// For the RoomDb
@Database(
    entities = [(Event::class)], // all entities which are in this database
    version = 5,
    exportSchema = false
)
abstract class EventsDB : RoomDatabase() {

    abstract fun eventsDao(): EventsDao

    companion object {
        private var INSTANCE: EventsDB? = null

        fun getDatabase(context: Context): EventsDB {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also {
                    INSTANCE = it
                }
            }
        }

        private fun buildDatabase(context: Context): EventsDB {
            return Room.databaseBuilder(context, EventsDB::class.java, "event_database")
                .fallbackToDestructiveMigration()
                .build()
        }

    }

}
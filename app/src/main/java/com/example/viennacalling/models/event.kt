package com.example.viennacalling.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = true)
    val eventId: Int? = null,
    val id: String = "",
    var title: String = "",
    val description: String = "",
    val category: String = "",
    val link: String = "",
    val subject: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val point: String = "",
    val streetAddress: String = "",
    val plz: String = "",
    val images: String = "",
    var isInList: Boolean = false,
    var uuid: String? = null,
)

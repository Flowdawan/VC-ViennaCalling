package at.deflow.viennacalling.models

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "events")
data class Event(
    @SerializedName("id")
    @PrimaryKey
    val id: String = "",
    @SerializedName("title")
    var title: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("category")
    val category: String = "",
    @SerializedName("link")
    val link: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("days")
    val days: String = "",
    @SerializedName("subject")
    val subject: String = "",
    @SerializedName("startTime")
    val startTime: String = "",
    @SerializedName("endTime")
    val endTime: String = "",
    @SerializedName("startHour")
    val startHour: String = "",
    @SerializedName("startMin")
    val startMin: String = "",
    @SerializedName("point")
    val point: String = "",
    @SerializedName("streetAddress")
    val streetAddress: String = "",
    @SerializedName("plz")
    val plz: String = "",
    @SerializedName("images")
    val images: String? = "",
    @SerializedName("uuid")
    var uuid: String? = "0",
)
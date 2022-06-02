package com.example.viennacalling.dao

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.viennacalling.models.Event
import com.example.viennacalling.models.xml.RssFeed
import com.example.viennacalling.retrofit.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val TAG = "EventsDao"

@Dao
interface EventsDao {

    @Query("SELECT * FROM events")
    fun getAllEvents(): Flow<List<Event>>

    @Update
    suspend fun editEvent(event: Event)

    @Query("DELETE FROM events WHERE title = :title")
    suspend fun deleteEvent(title: String)

    @Insert
    suspend fun addEvent(event: Event)

    @Query("SELECT * FROM events WHERE title = :title")
    suspend fun getEventByName(title: String): Event

    @Query("SELECT * FROM events WHERE id = :id")
    suspend fun getEventById(id: Long): Event


    fun fetchEventRssFeed(eventList: MutableList<Event>) {
        val eventImagesList =  listOf("https://cdn.pixabay.com/photo/2016/11/29/06/17/audience-1867754_1280.jpg",
            "https://cdn.pixabay.com/photo/2015/11/22/19/04/crowd-1056764_1280.jpg",
            "https://cdn.pixabay.com/photo/2016/11/18/17/47/iphone-1836071_1280.jpg",
            "https://cdn.pixabay.com/photo/2016/11/22/19/15/hand-1850120_1280.jpg",
            "https://cdn.pixabay.com/photo/2016/11/22/19/15/audience-1850119_1280.jpg",
            "https://cdn.pixabay.com/photo/2015/03/08/17/25/musician-664432_1280.jpg",
            "https://cdn.pixabay.com/photo/2016/11/22/18/56/audience-1850022_1280.jpg",)

        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val current = LocalDateTime.now()
        val nextYear = current.plusYears(1).format(formatter) // To get all events from today + 1 year
        val currentFormatted = current.format(formatter)

        var retrofitInstance = RetrofitInstance.api.getEventList(
            layout = "rss-vadb_neu",
            type = "R",
            hmwd = "d",
            category = "6",
            startDate = currentFormatted.toString(),
            endDate = nextYear.toString()
        )

        retrofitInstance.enqueue(object : Callback<RssFeed?> {
            override fun onResponse(call: Call<RssFeed?>, response: Response<RssFeed?>) {
                if (response.isSuccessful) {
                    var eventCounter: Int = 0
                    val apiResponse: RssFeed? = response.body()
                    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                    // API response
                    response.body()!!.channel?.eventList?.forEach { event ->
                        var startDate: String = ""
                        var endDate: String = ""

                        var pointId = event.point?.replace("[.+\\s]".toRegex(), "")

                        if(event.dtstart != null && event.dtstart!!.first() != 'T') {
                            startDate = LocalDateTime.parse(event.dtstart?.take(19) ?: "").format(formatter)
                                .toString()
                        }

                        if(event.dtend != null && event.dtend!!.first() != 'T') {
                            endDate = LocalDateTime.parse(event.dtend?.take(19) ?: "").format(formatter)
                                .toString()
                        }

                        var newEvent = Event(
                            id = "$eventCounter-${event.dtstart?.replace("+", "")}-$pointId".replace("-$".toRegex(), ""),
                            title = event.titleList?.get(0)?.replaceFirstChar { it.uppercase() } ?: "Es ist leider kein Titel vorhanden",
                            category = event.category ?: "Es ist leider keine Kategorie vorhanden",
                            description = Jsoup.parse(event.descriptionList?.get(0)).text()  ?: "Es ist leider keine Beschreibung vorhanden",
                            link = event.link ?: "Es ist leider kein Link vorhanden",
                            subject = event.subject ?: "Es ist leider kein Subject vorhanden",
                            startTime = startDate,
                            endTime = endDate,
                            point = event.point ?: "",
                            streetAddress = event.location?.street_address ?: "Es ist leider keine Adresse vorhanden",
                            plz = event.location?.postal_code ?: "",
                            images = event.content?.url ?: eventImagesList.random()
                        )

                        eventList.add(newEvent)
                        eventCounter += 1
                    }
                } else {
                    Log.d(TAG, "We got no response")
                }
            }

            override fun onFailure(call: Call<RssFeed?>, t: Throwable) {
                Log.d(
                    TAG,
                    "We got a exception while trying to fetch the xml feed: ${t.stackTrace}"
                )
            }
        })
    }

}
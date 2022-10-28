package at.deflow.viennacalling.dao

import android.util.Log
import androidx.room.*
import at.deflow.viennacalling.models.Event
import at.deflow.viennacalling.retrofit.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URLDecoder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val TAG = "EventsDao"

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


    fun fetchEventRssFeed(
        eventList: MutableList<Event>,
        eventListInitial: ArrayList<Event>,
    ) {

        // If there are no images we select random one of these stock photos)
        val eventImagesListDefault = listOf(
            "https://cdn.pixabay.com/photo/2016/11/29/06/17/audience-1867754_1280.jpg",
            "https://cdn.pixabay.com/photo/2015/11/22/19/04/crowd-1056764_1280.jpg",
            "https://cdn.pixabay.com/photo/2019/10/15/03/16/black-and-white-4550471_1280.jpg",
            "https://cdn.pixabay.com/photo/2017/03/25/09/51/party-2173187_1280.jpg",
            "https://cdn.pixabay.com/photo/2016/11/23/15/48/audience-1853662_1280.jpg",
            "https://cdn.pixabay.com/photo/2014/07/09/12/17/live-concert-388160_1280.jpg",
            "https://cdn.pixabay.com/photo/2016/11/18/17/47/iphone-1836071_1280.jpg",
            "https://cdn.pixabay.com/photo/2016/11/22/19/15/hand-1850120_1280.jpg",
            "https://cdn.pixabay.com/photo/2016/11/22/19/15/audience-1850119_1280.jpg",
            "https://cdn.pixabay.com/photo/2015/03/08/17/25/musician-664432_1280.jpg",
            "https://cdn.pixabay.com/photo/2016/11/22/18/56/audience-1850022_1280.jpg",
            "https://cdn.pixabay.com/photo/2019/06/11/16/14/vienna-4267377_960_720.jpg",
            "https://cdn.pixabay.com/photo/2019/08/13/17/17/vienna-state-opera-4403839_960_720.jpg",
            "https://cdn.pixabay.com/photo/2018/12/17/14/20/vienna-3880488_960_720.jpg",
        )

        val retrofitInstance = RetrofitInstance.api.getEventListAll()

        // Here we make the actual request to our api and if we get correct data back (which is basically models.xml.* data classes)
        // we use it to create a new event for each item from the rss feed and then store it in our event view model
        retrofitInstance.enqueue(object : Callback<ArrayList<Event>?> {
            override fun onResponse(
                call: Call<ArrayList<Event>?>,
                response: Response<ArrayList<Event>?>
            ) {

                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                Log.d(TAG, "HI")

                // API response
                response.body()!!.forEach { event ->
                    Log.d(TAG, event.title)
                    Log.d(TAG, URLDecoder.decode(event.title))

                    if(event.title == "" || (event.startTime == "" && event.description == "")) {
                        return@forEach
                    }
                    var startDate = ""
                    var endDate = ""

                    // because we want to use the point for our unique id for every object we manipulate it a little
                    val pointId = event.point.replace("[.+\\s]".toRegex(), "")

                    if (event.startTime != "" && event.startTime.first() != 'T') {
                        startDate =
                            LocalDateTime.parse(event.startTime.take(19) ?: "").format(formatter)
                                .toString()
                    }

                    if (event.endTime != "" && event.endTime.first() != 'T') {
                        endDate =
                            LocalDateTime.parse(event.endTime.take(19) ?: "").format(formatter)
                                .toString()
                    }

                    // New event is created and then saved in our list which is then displayed in the home screen
                    val newEvent = Event(
                        id = "${
                            event.startTime.replace(
                                "+",
                                ""
                            )
                        }-$pointId".replace("-$".toRegex(), ""),
                        title = if (event.title != "") event.title else "Es ist leider kein Titel vorhanden",
                        description = if (event.description != "") event.description else "Es ist leider keine Beschreibung vorhanden",
                        category = event.category,
                        link = if (event.link != "") event.link else "Es ist leider kein Link vorhanden",
                        url = event.url,
                        subject = event.subject,
                        startTime = startDate,
                        endTime = endDate,
                        startHour = event.startHour,
                        startMin = event.startMin,
                        point = event.point,
                        streetAddress = event.streetAddress,
                        plz = event.plz,
                        images = if (event.images != "") event.images
                        else
                            when (event.category) {
                                "party" -> eventImagesListDefault.random()
                                "sightseeing" -> eventImagesListDefault.random()
                                "culture" -> eventImagesListDefault.random()
                                else -> eventImagesListDefault.random()
                            }
                    )
                    eventList.add(newEvent)
                    eventListInitial.add(newEvent)
                }
            }

            override fun onFailure(call: Call<ArrayList<Event>?>, t: Throwable) {
                Log.d(TAG, "Failed to fetch data");
            }
        })
    }
}
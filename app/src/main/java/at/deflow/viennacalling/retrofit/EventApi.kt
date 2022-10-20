package at.deflow.viennacalling.retrofit

import at.deflow.viennacalling.models.Event
import retrofit2.Call
import retrofit2.http.GET

interface EventApi {
    @GET("api/getAllEvents")
    fun getEventListAll(): Call<ArrayList<Event>>
}
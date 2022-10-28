package at.deflow.viennacalling.retrofit

import at.deflow.viennacalling.models.Event
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers

interface EventApi {
    @GET("api/getAllEvents")
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
        "Platform: android")
    fun getEventListAll(): Call<ArrayList<Event>>
}
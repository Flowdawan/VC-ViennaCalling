package at.deflow.viennacalling.retrofit

import retrofit2.http.GET
import retrofit2.http.Headers

interface EventApi {
    @GET("api/getAllEvents")
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
        "Platform: android")
    suspend fun getEventListAll(): List<ApiEvent>
}

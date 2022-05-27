package com.example.viennacalling.retrofit

import com.example.viennacalling.models.Event
import com.example.viennacalling.models.Goa
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://www.goabase.net/api/party/json/"

interface APIService {
    @GET("country=at&limit=50")
    suspend fun getEvents(): List<Goa>

    companion object {
        var apiService: APIService? = null
        fun getInstance(): APIService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(APIService::class.java)
            }
            return apiService!!
        }
    }
}

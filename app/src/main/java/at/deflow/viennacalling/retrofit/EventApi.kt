package at.deflow.viennacalling.retrofit

import at.deflow.viennacalling.models.xml.RssFeed
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EventApi {
    @GET("vadb/internet/AdvPrSrv.asp")
    fun getEventList(
        @Query("Layout") layout: String,
        @Query("Type") type: String,
        @Query("hmwd") hmwd: String,
        @Query("KATEGORIE_ID") category: String,
        @Query("HAUPTKATEGORIEN") subCategory: String,
        @Query("vie_range-from") startDate: String,
        @Query("vie_range-to") endDate: String
    ): Call<RssFeed>
}
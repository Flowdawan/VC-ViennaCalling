package at.deflow.viennacalling.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: EventApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://vcapi.deflow.at/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EventApi::class.java)
    }
}
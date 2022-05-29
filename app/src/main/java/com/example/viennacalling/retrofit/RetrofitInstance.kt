package com.example.viennacalling.retrofit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object RetrofitInstance {
    val api: EventApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.wien.gv.at")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
            .create(EventApi::class.java)
    }
}
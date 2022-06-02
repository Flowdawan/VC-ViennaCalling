package com.example.viennacalling.retrofit

import okhttp3.OkHttpClient
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object RetrofitInstance {
    val api: EventApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.wien.gv.at/")
            .client(OkHttpClient())
            .addConverterFactory(
                SimpleXmlConverterFactory.createNonStrict(
                    Persister(AnnotationStrategy())
                )
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(EventApi::class.java)
    }
}
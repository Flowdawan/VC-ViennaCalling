package at.deflow.viennacalling.retrofit

import okhttp3.OkHttpClient
import org.simpleframework.xml.core.Persister
import org.simpleframework.xml.stream.Format
import org.simpleframework.xml.stream.HyphenStyle
import org.simpleframework.xml.stream.Verbosity
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object RetrofitInstance {
    var format = Format(0, null, HyphenStyle(), Verbosity.HIGH)
    var persister: Persister = Persister(format)
    val api: EventApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.wien.gv.at/")
            .client(OkHttpClient())
            .addConverterFactory(
                SimpleXmlConverterFactory.create(persister)
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(EventApi::class.java)
    }
}
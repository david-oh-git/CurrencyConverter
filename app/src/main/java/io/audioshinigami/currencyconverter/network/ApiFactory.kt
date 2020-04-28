package io.audioshinigami.currencyconverter.network

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import io.audioshinigami.currencyconverter.models.RateResponseParser
import io.audioshinigami.currencyconverter.models.RateResponse
import io.audioshinigami.currencyconverter.utils.api_key
import io.audioshinigami.currencyconverter.utils.base_url
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    //Creating Auth Interceptor to add api_key query in front of all the requests.
    private val authInterceptor = Interceptor { chain ->
        val newUrl = chain.request().url()
            .newBuilder()
            .addQueryParameter("apiKey", api_key)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    private val logginingInterceptor = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

//    client for http request url
    private val httpClient = OkHttpClient().newBuilder()
    .addInterceptor(authInterceptor)
    .addInterceptor(logginingInterceptor)
    .build()

    private val gsonBuilder = GsonBuilder()


    private fun retrofit(): Retrofit {

//        gsonBuilder.registerTypeAdapter(RateResponse::class.java, RateResponseParser())

        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    val rateApi : ConverterApi = retrofit().create(ConverterApi::class.java)

} /*END*/
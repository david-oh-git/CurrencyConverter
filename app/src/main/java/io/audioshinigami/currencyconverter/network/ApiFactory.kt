/*
 * Copyright 2020 Audio Shinigami Studio.
 *  All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.audioshinigami.currencyconverter.network

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import io.audioshinigami.currencyconverter.BuildConfig
import io.audioshinigami.currencyconverter.models.RateResponseParser
import io.audioshinigami.currencyconverter.models.RateResponse
import io.audioshinigami.currencyconverter.utils.BASE_URL
import io.audioshinigami.currencyconverter.utils.api_key
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
//    client for http request url
    private val httpClient: OkHttpClient by lazy {
        addHttpClient()
    }

    private val gsonBuilder = GsonBuilder()

    private fun addHttpClient(): OkHttpClient =

        if( BuildConfig.DEBUG){
            val loggingInterceptor = HttpLoggingInterceptor()
                .apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }

            OkHttpClient().newBuilder()
                .addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()

        }else{
            OkHttpClient().newBuilder()
                .addInterceptor(authInterceptor)
                .build()
        }

    private fun retrofit(): Retrofit {

        gsonBuilder.registerTypeAdapter(RateResponse::class.java, RateResponseParser())

        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create( gsonBuilder.create()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    val rateApi : ConverterApi = retrofit().create(ConverterApi::class.java)

} /*END*/
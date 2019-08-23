package io.audioshinigami.currencyconverter.network

import io.audioshinigami.currencyconverter.models.CurrencyRate
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ConverterApi {

    @GET("/api/v7/convert")
    suspend fun getRate(
        @Query("q") currencyCode: String,
        @Query("compact") compact: String = "ultra"): Deferred<Response<CurrencyRate>>
}
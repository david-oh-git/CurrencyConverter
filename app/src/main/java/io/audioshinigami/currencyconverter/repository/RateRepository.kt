package io.audioshinigami.currencyconverter.repository

import io.audioshinigami.currencyconverter.models.Rate
import io.audioshinigami.currencyconverter.models.RateResponse
import io.audioshinigami.currencyconverter.network.ConverterApi

class RateRepository(private val api: ConverterApi): BaseRepository() {



    suspend fun fetchRateFromApi(currencyCode: String): RateResponse? {

        return safeApiCall(
            call = { api.getRate(currencyCode = currencyCode).await() },
            errorMsg = "Error fetching currency rate"
        )

    }

    fun getRate(code: String, result: Map<String, String>): Rate {

        /*extract data gotten from API & assigns to Rate class*/
        val rate = Rate(code)

        for( (k,v) in  result ){
            if( k == code )
                rate.rateValue = v.toDouble()
            else
                rate.reverseRate = v.toDouble()
        } /*end FOR*/

        return rate
    }
}
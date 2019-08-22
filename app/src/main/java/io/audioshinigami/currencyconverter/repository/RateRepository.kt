package io.audioshinigami.currencyconverter.repository

import io.audioshinigami.currencyconverter.models.CurrencyRate
import io.audioshinigami.currencyconverter.network.ConverterApi
import org.json.JSONArray
import org.json.JSONObject

class RateRepository(private val api: ConverterApi): BaseRepository() {



    suspend fun getRate(currencyCode: String): CurrencyRate? {

        return safeApiCall(
            call = { api.getRate(currencyCode = currencyCode).await() },
            errorMsg = "Error fetching currency rate"
        )

    }
}
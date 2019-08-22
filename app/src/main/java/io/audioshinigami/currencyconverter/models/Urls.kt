package io.audioshinigami.currencyconverter.models

import io.audioshinigami.currencyconverter.utils.api_key
import io.audioshinigami.currencyconverter.utils.toApiString

object Urls {

    private const val base_url = "https://free.currconv.com"

    private fun getQueryString(currency: String, toCurrency: String ): String
            = "${currency}_$toCurrency,${toCurrency}_$currency"

    fun getConvertUrl(currency: String, toCurrency: String): String {
        val URL = "$base_url/api/v7/convert?"
        val queryString = getQueryString(currency, toCurrency)

        val PARAMS = mutableMapOf<String, String>()
        PARAMS["action"] = "query"
        PARAMS["q"] = queryString
        PARAMS["compact"] = "ultra"
        PARAMS["apiKey"] = api_key

        val paramsUrl =  PARAMS.toApiString()

        return "$base_url$paramsUrl"
    }
}
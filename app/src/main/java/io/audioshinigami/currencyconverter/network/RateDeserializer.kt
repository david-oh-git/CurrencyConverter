package io.audioshinigami.currencyconverter.network

import com.google.gson.*
import io.audioshinigami.currencyconverter.data.Rate
import io.audioshinigami.currencyconverter.utils.Date
import timber.log.Timber
import java.lang.reflect.Type

class RateDeserializer: JsonDeserializer<RateResponse> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): RateResponse? {


        try {
            val rates = readServiceUrlMap(json?.asJsonObject)

            if(rates.isNotEmpty())
                return RateResponse(rates)

        }catch (e: JsonSyntaxException){

            Timber.d( "Parser error:\n error message : ${e.printStackTrace()}")
            return null
        }

        return null
    }

    @Throws(JsonSyntaxException::class)
    private fun readServiceUrlMap(jsonObject: JsonObject?): List<Rate> {
        val currencyRates = mutableListOf<Rate>()

        if(jsonObject == null )
            return currencyRates

        val gson = Gson()


        for(entry: Map.Entry<String, JsonElement> in jsonObject.entrySet()){
            val key = entry.key
            val value: String = gson.fromJson(entry.value, String::class.java)

            val rate = Rate(0, key, value.toDouble(), Date.currentDate)
            currencyRates.add(rate)
        } /*end FOR*/

        return currencyRates
    }
}
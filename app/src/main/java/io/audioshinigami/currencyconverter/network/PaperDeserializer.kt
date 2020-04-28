package io.audioshinigami.currencyconverter.network

import com.google.gson.*
import io.audioshinigami.currencyconverter.data.Paper
import timber.log.Timber
import java.lang.reflect.Type

class PaperDeserializer: JsonDeserializer<PaperResponse> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): PaperResponse? {


        try {
            val allCurrencies = readServiceUrlMap(json?.asJsonObject)

            if(allCurrencies.isNotEmpty())
                return PaperResponse( allCurrencies )

        }catch (e: JsonSyntaxException){

            Timber.d( "Parser error:\n error message : ${e.printStackTrace()}")
            return null
        }

        return null
    }

    @Throws(JsonSyntaxException::class)
    private fun readServiceUrlMap(jsonObject: JsonObject?): List<Paper> {
        val allCurrencies = mutableListOf<Paper>()

        if(jsonObject == null )
            return allCurrencies

        val gson = Gson()

        val resultObject = jsonObject.getAsJsonObject("results")

        for(entry: Map.Entry<String, JsonElement> in resultObject.entrySet()){
            val paper: Paper = gson.fromJson(entry.value, Paper::class.java)
            allCurrencies.add(paper)

        } /*end FOR*/

        return allCurrencies
    }
}
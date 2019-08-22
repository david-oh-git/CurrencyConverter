package io.audioshinigami.currencyconverter.models

import android.util.Log
import com.google.gson.*
import java.lang.reflect.Type

class CurrencyParser: JsonDeserializer<CurrencyRate> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): CurrencyRate? {

        val result = CurrencyRate()

        try {
            val map = readServiceUrlMap(json?.asJsonObject)

            if(map != null)
                result.rate = map
        }catch (e: JsonSyntaxException){

            Log.d("TAGU", "Parser error:\n error message : ${e.printStackTrace()}")
            return null
        }

        return result
    } /*end Deserial*/

    @Throws(JsonSyntaxException::class)
    private fun readServiceUrlMap(jsonObject: JsonObject?): HashMap<String, String>? {
        if(jsonObject == null )
            return null

        val gson = Gson()
        val currencyRates = HashMap<String, String>()


        for(entry: Map.Entry<String, JsonElement> in jsonObject.entrySet()){
            val key = entry.key
            Log.d("TAGU"," value is ${entry.value}")
            val value: String = gson.fromJson(entry.value, String::class.java)
            currencyRates[key] = value
        } /*end FOR*/

        return currencyRates
    }
}
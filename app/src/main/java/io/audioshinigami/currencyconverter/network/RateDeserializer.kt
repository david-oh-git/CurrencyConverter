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
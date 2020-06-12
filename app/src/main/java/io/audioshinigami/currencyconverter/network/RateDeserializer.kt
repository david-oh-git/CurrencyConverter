/*
 * MIT License
 *
 * Copyright (c) 2020  David Osemwota
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
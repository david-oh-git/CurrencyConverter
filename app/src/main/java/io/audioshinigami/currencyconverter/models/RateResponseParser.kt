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

package io.audioshinigami.currencyconverter.models

import com.google.gson.*
import timber.log.Timber
import java.lang.reflect.Type

class RateResponseParser: JsonDeserializer<RateResponse> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): RateResponse? {

        val result = RateResponse()

        try {
            val map = readServiceUrlMap(json?.asJsonObject)

            if(map != null)
                result.rate = map
        }catch (e: JsonSyntaxException){

            Timber.d( "Parser error:\n error message : ${e.printStackTrace()}")
            return null
        }

        return result
    } /*end Deserialize*/

    @Throws(JsonSyntaxException::class)
    private fun readServiceUrlMap(jsonObject: JsonObject?): HashMap<String, String>? {
        if(jsonObject == null )
            return null

        val gson = Gson()
        val currencyRates = HashMap<String, String>()


        for(entry: Map.Entry<String, JsonElement> in jsonObject.entrySet()){
            val key = entry.key
            val value: String = gson.fromJson(entry.value, String::class.java)
            currencyRates[key] = value
        } /*end FOR*/

        return currencyRates
    }
}
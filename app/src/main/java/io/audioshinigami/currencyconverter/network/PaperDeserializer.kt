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
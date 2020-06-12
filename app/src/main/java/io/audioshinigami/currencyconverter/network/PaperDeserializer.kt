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
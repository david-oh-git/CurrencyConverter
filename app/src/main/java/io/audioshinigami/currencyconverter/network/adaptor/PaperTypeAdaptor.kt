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

package io.audioshinigami.currencyconverter.network.adaptor

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import io.audioshinigami.currencyconverter.data.Paper
import java.io.IOException

class PaperTypeAdaptor: TypeAdapter< List<Paper>>() {

    private val code = "currencyId"
    private val currencyName = "currencyName"
    private val countryName = "name"
    private val currencySymbol = "currencySymbol"

    override fun write(out: JsonWriter, value: List<Paper>?) {
        if( value == null){
            out.nullValue()
            return
        }

        writeList(out, value)
    }

    @Throws(IOException::class)
    override fun read(reader: JsonReader): List<Paper>? {
        try {
            val token = reader.peek()

            if( token == JsonToken.NULL ){
                reader.nextNull()

                return null
            }

            if ( token == JsonToken.BEGIN_ARRAY ){
                return readList(reader)
            }
        } catch (e: IOException){
            return null
        }

        return listOf()
    }

    private fun writeList(out: JsonWriter, values: List<Paper>){

        out.beginObject()
        for( paper in values ){
            out.beginObject()
            out.name(code).value(paper.code)
            out.name(currencyName).value(paper.name)
            out.name(countryName).value(paper.countryName)
            out.name(currencySymbol).value(paper.symbol)
            out.endObject()
        }
        out.endObject()
    }

    private fun readList(reader: JsonReader): List<Paper> {
        val result: List<Paper> = listOf()

        reader.beginArray()

        while (reader.hasNext() ){

        }

        return result
    }
}
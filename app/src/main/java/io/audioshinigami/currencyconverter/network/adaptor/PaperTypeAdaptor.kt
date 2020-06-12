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
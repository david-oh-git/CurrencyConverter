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

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

abstract class CustomTypeAdaptorFactory<T>(
    private val customClass: Class<T>
) : TypeAdapterFactory {

    @Suppress("UNCHECKED_CAST")
    override fun <K : Any?> create(gson: Gson, type: TypeToken<K>): TypeAdapter<K>? {
        return if (type.rawType == customClass) customClassAdaptor(gson, type  as TypeToken<T> ) as TypeAdapter<K>
        else null
    }

    private fun customClassAdaptor(gson: Gson, type: TypeToken<T>?): TypeAdapter<T> {
        val delegate = gson.getDelegateAdapter(this, type)
        val elementAdaptor =  gson.getAdapter(JsonElement::class.java)

        return object : TypeAdapter<T>(){
            override fun write(out: JsonWriter?, value: T) {
                val tree = delegate.toJsonTree(value)
                beforeWrite(value, tree)
                elementAdaptor.write(out, tree)
            }

            override fun read(`in`: JsonReader?): T {
                val tree = elementAdaptor.read(`in`)
                afterRead(tree)

                return delegate.fromJsonTree(tree)
            }
        }
    }

    fun beforeWrite(source: T, toSerialize: JsonElement){

    }

    fun afterRead(deserialized: JsonElement){

    }
}
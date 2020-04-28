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
package io.audioshinigami.currencyconverter.data.source.local

import io.audioshinigami.currencyconverter.data.SharedPreferenceSource

class FakePreferenceSourceTest(
    var sharedPreference: MutableMap<String, Any> = mutableMapOf()
) : SharedPreferenceSource {

    override suspend fun save(key: String, value: String) {
        sharedPreference[key] = value
    }

    override suspend fun save(key: String, value: Boolean) {
        sharedPreference[key] = value
    }

    override suspend fun save(key: String, value: Int) {
        sharedPreference[key] = value
    }

    override suspend fun getString(key: String): String? {
        return ( sharedPreference[key] as String?)
    }

    override suspend fun getBoolean(key: String): Boolean {
        return ( sharedPreference[key] as Boolean)
    }

    override suspend fun getInt(key: String): Int {
        return ( sharedPreference[key] as Int)
    }

    override suspend fun remove(key: String) {
        sharedPreference.remove(key)
    }
}
package io.audioshinigami.currencyconverter.models

import android.content.res.Resources
import io.audioshinigami.currencyconverter.R

object CurrencyData {
    private var codes: ArrayList<String>? = null



    fun getCodes(resources: Resources): ArrayList<String>? =
        codes?: synchronized(this){
            resources.getStringArray(R.array.countries).toCollection(ArrayList())
        }
}
package io.audioshinigami.currencyconverter.repository

import android.content.res.TypedArray
import io.audioshinigami.currencyconverter.App
import io.audioshinigami.currencyconverter.R
import io.audioshinigami.currencyconverter.models.CurrencyItem
import io.audioshinigami.currencyconverter.utils.currency_codes_total

/*
* A repository for getting all currency code eg. NGN or USD
* and flag icon resource id
* ++ also use the inno to generate all [Curr] needed
*/

class CodeRepository(
    application: App = App.instance
    ) {

    private var codes: ArrayList<String>? = null
    private val _flags: ArrayList<Int> = arrayListOf()
    private val context = application.applicationContext
    private val allCurrencyItems: ArrayList<CurrencyItem> = arrayListOf()

    fun getCodes(): ArrayList<String> =
        synchronized(this){
            codes ?: context.resources.getStringArray(R.array.countries).toCollection(ArrayList())
        }

    fun getFlags(): ArrayList<Int> =
        synchronized(this){
            if( _flags.isEmpty()){
                val flags: TypedArray = context.resources.obtainTypedArray(R.array.flags_codes)

                for( index in 0 until currency_codes_total){
                    val id: Int = flags.getResourceId(index, -1)
                    _flags.add(index, id)
                }
                flags.recycle()
            }
           _flags
        }

    fun getAllCurrency(): ArrayList<CurrencyItem> =
        synchronized(this){
            getFlags().forEachIndexed{
                index, flag ->
                allCurrencyItems.add(index, CurrencyItem(getCodes()[index], flag))
            }
            allCurrencyItems
        }

}/*END*/
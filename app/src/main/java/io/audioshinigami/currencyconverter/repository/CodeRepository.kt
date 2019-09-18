package io.audioshinigami.currencyconverter.repository

import io.audioshinigami.currencyconverter.App
import io.audioshinigami.currencyconverter.models.CurrencyItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/*
* A repository for getting all currency code eg. NGN or USD
* and flag icon resource id
* ++ also use the inno to generate all [Curr] needed
*/

class CodeRepository(
    private val application: App = App.instance
    ) {

    private var codes: ArrayList<String>? = null
    private var flages: ArrayList<Int>? = null
    private val context = application.applicationContext


    fun getCodes(): ArrayList<String> =
        synchronized(this){
            arrayListOf()
        }

    fun getFlags(): ArrayList<Int> =
        synchronized(this){
            arrayListOf()
        }

    fun getAllCurrency(): ArrayList<CurrencyItem> =
        synchronized(this){

            arrayListOf()
        }

}/*END*/
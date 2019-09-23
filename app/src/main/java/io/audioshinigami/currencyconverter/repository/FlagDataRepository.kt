package io.audioshinigami.currencyconverter.repository

import io.audioshinigami.currencyconverter.models.AllCurrencyItemsData
import io.audioshinigami.currencyconverter.models.CurrencyItem

/*
* A repository for getting all currency code eg. NGN or USD
* and flag icon resource id
* ++ also use the inno to generate all [Curr] needed
*/

class FlagDataRepository {

    fun getCodes(): ArrayList<String> =
        synchronized(this){
            AllCurrencyItemsData.codes
        }

    fun getFlags(): ArrayList<Int> =
        synchronized(this){
            AllCurrencyItemsData.flags
        }

    fun getAllCurrency(): ArrayList<CurrencyItem> =
        synchronized(this){
            AllCurrencyItemsData.currencyItems
        } //end sync

}/*END*/
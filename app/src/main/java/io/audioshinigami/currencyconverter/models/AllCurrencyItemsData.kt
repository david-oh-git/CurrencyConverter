package io.audioshinigami.currencyconverter.models

import android.content.res.TypedArray
import io.audioshinigami.currencyconverter.App
import io.audioshinigami.currencyconverter.R
import io.audioshinigami.currencyconverter.utils.currency_codes_total

object AllCurrencyItemsData {
    // gets the resource id for all flag icons
    val flags: ArrayList<Int>
        get() {
            val results: ArrayList<Int> = arrayListOf()
            val images: TypedArray =
                App.instance.applicationContext.resources.obtainTypedArray(R.array.flags_codes)

            for( index in 0 until currency_codes_total ){
                val id: Int = images.getResourceId(index, -1)
                results.add(index, id)
            }
            images.recycle()
             return results
        }

    // gets all currency codes eg NGN , USD
    val codes: ArrayList<String>
    get() {
        return App.instance
            .applicationContext.resources.getStringArray(R.array.countries).toCollection(ArrayList())
    }

    // all [CurrencyItem] data
    val currencyItems: ArrayList<CurrencyItem> by lazy { allItems() }

    private fun allItems(): ArrayList<CurrencyItem> {
        val items = arrayListOf<CurrencyItem>()

        flags.forEachIndexed{
            index, flag ->
            items.add(index, CurrencyItem(codes[index], flag))
        }
        return items
    }


} // AllCurrencyItems
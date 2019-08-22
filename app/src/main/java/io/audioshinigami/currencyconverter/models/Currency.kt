package io.audioshinigami.currencyconverter.models

import io.audioshinigami.currencyconverter.R

data class Currency(var name: String , var icon: Int ){

    companion object{
        private var allcountries: ArrayList<Currency> = ArrayList()

        fun getCountriesData(): ArrayList<Currency> {


            allcountries.add(Currency("NGN", R.drawable.nigeria))
            allcountries.add(Currency("AFN", R.drawable.afghanistan))
            allcountries.add(Currency("XCD", R.drawable.anguilla))
            allcountries.add(Currency("AUD", R.drawable.australia))
            allcountries.add(Currency("BDT", R.drawable.bangladesh))
            allcountries.add(Currency("USD", R.drawable.united_states_of_america))

            return allcountries
        }

    }
} /*end Currency*/
package io.audioshinigami.currencyconverter.models

import io.audioshinigami.currencyconverter.R

data class Currency(var code: String, var icon: Int ){

    companion object{
        @Volatile private var allcountries: ArrayList<Currency>? = null

        fun getCountriesData(): ArrayList<Currency>? {
            /*of course i copied this from the internet */

            return allcountries?: synchronized(this){

                ArrayList<Currency>().also {
                    it.add(Currency("NGN", R.drawable.nigeria))
                    it.add(Currency("AFN", R.drawable.afghanistan))
                    it.add(Currency("XCD", R.drawable.anguilla))
                    it.add(Currency("AUD", R.drawable.australia))
                    it.add(Currency("BDT", R.drawable.bangladesh))
                    it.add(Currency("USD", R.drawable.united_states_of_america))
                    it.add(Currency("XOF", R.drawable.benin))
                    it.add(Currency("BRL", R.drawable.brazil))
                    it.add(Currency("KHR", R.drawable.cambodia))
                    it.add(Currency("XAF", R.drawable.chad))
                    it.add(Currency("CUP", R.drawable.cuba))
                    it.add(Currency("EUR", R.drawable.finland))
                    it.add(Currency("GEL", R.drawable.georgia))
                    it.add(Currency("HTG", R.drawable.haiti))
                }

            } /*end SYNC*/

        } /*end getCountries*/

    }
} /*end Currency*/
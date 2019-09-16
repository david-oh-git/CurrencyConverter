package io.audioshinigami.currencyconverter.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CurrencyConvertVMFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CurrencyConvertViewModel::class.java)){
            return CurrencyConvertViewModel() as T
        }
        throw IllegalArgumentException("Unknow ViewModel Class")
    }
}
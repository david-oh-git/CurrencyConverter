package io.audioshinigami.currencyconverter.selectcurrency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.audioshinigami.currencyconverter.repository.FlagDataRepository

class CurrencySelectVMFactory(private val flagDataRepository: FlagDataRepository) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if( modelClass.isAssignableFrom(CurrencySelectViewModel::class.java)){
            return CurrencySelectViewModel(flagDataRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
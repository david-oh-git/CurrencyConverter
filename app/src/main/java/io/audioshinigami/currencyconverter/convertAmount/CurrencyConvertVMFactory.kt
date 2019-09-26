package io.audioshinigami.currencyconverter.convertAmount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.audioshinigami.currencyconverter.repository.FlagDataRepository
import io.audioshinigami.currencyconverter.repository.RateRepository

class CurrencyConvertVMFactory(private val flagDataRepository: FlagDataRepository,
                               private val rateRepository: RateRepository
                               ): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CurrencyConvertViewModel::class.java)){
            return CurrencyConvertViewModel(flagDataRepository, rateRepository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel Class")
    }
}
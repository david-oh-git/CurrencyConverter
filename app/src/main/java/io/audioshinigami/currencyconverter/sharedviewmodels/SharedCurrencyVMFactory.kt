package io.audioshinigami.currencyconverter.sharedviewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.audioshinigami.currencyconverter.repository.FlagDataRepository
import io.audioshinigami.currencyconverter.repository.RateRepository

class SharedCurrencyVMFactory(private val flagDataRepository: FlagDataRepository,
                              private val rateRepository: RateRepository
                               ): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SharedCurrencyViewModel::class.java)){
            return SharedCurrencyViewModel(
                flagDataRepository,
                rateRepository
            ) as T
        }
        throw IllegalArgumentException("Unknow ViewModel Class")
    }
}
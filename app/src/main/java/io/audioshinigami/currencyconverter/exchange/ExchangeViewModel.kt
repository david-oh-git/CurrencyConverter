package io.audioshinigami.currencyconverter.exchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.audioshinigami.currencyconverter.data.DatabaseSource

class ExchangeViewModel(
    private val databaseSource: DatabaseSource
) : ViewModel() {

    // rate cache from db

    fun checkRate( code: String ) = false
}

@Suppress("UNCHECKED_CAST")
class ExchangeViewModelFactory(
    private val databaseSource: DatabaseSource
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ( ExchangeViewModel(databaseSource) as T)
}
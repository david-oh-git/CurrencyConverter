package io.audioshinigami.currencyconverter.exchange

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import io.audioshinigami.currencyconverter.data.DatabaseSource
import io.audioshinigami.currencyconverter.data.Paper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExchangeViewModel(
    private val databaseSource: DatabaseSource
) : ViewModel() {

    val _currencies: MutableLiveData<List<Paper>> = MutableLiveData( mutableListOf() )


    init {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){ databaseSource.getAllPapers() }
            _currencies.postValue(result)
        }
    }

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
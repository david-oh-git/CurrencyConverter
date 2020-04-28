package io.audioshinigami.currencyconverter.exchange

import androidx.lifecycle.*
import io.audioshinigami.currencyconverter.data.DatabaseSource
import io.audioshinigami.currencyconverter.data.Paper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class ExchangeViewModel(
    private val databaseSource: DatabaseSource
) : ViewModel() {

    private val _currencies: MutableLiveData<List<Paper>> = MutableLiveData( mutableListOf() )


    init {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){ databaseSource.getAllPapers() }
            _currencies.postValue(result)

            Timber.d("data size is ${_currencies.value?.size}")
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
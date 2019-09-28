package io.audioshinigami.currencyconverter.selectcurrency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.audioshinigami.currencyconverter.models.CurrencyItem
import io.audioshinigami.currencyconverter.repository.FlagDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrencySelectViewModel(private val flagDataRepository: FlagDataRepository) : ViewModel() {

    // 2 way binding
    private val _currencyItems = MutableLiveData<ArrayList<CurrencyItem>>().apply { value = arrayListOf() }
    val currencyItems: LiveData<ArrayList<CurrencyItem>> = _currencyItems

    fun loadCurrencyItems(){
        // populates currencyItems with data
        viewModelScope.launch(Dispatchers.Main){

            val data = withContext(Dispatchers.IO){ flagDataRepository.getAllCurrency() }
            _currencyItems.value = data
        }

    }
}

package io.audioshinigami.currencyconverter.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.audioshinigami.currencyconverter.convertAmount.CurrencyConvertVMFactory
import io.audioshinigami.currencyconverter.convertAmount.CurrencyConvertViewModel
import io.audioshinigami.currencyconverter.network.ApiFactory
import io.audioshinigami.currencyconverter.repository.FlagDataRepository
import io.audioshinigami.currencyconverter.repository.RateRepository


fun <T : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>): T {

    return when{
        viewModelClass.isInstance(CurrencyConvertViewModel() ) -> ViewModelProvider(this, setUpCurrencyConvertFactory()).get(viewModelClass)
        else -> ViewModelProvider(this).get(viewModelClass)
    }
} // END obtainViewModel

private fun setUpCurrencyConvertFactory(): CurrencyConvertVMFactory {
    return CurrencyConvertVMFactory(FlagDataRepository(), RateRepository(ApiFactory.rateApi))
}
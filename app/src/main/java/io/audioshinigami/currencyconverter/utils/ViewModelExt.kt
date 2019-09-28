package io.audioshinigami.currencyconverter.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.audioshinigami.currencyconverter.convertAmount.CurrencyConvertVMFactory
import io.audioshinigami.currencyconverter.convertAmount.CurrencyConvertViewModel
import io.audioshinigami.currencyconverter.network.ApiFactory
import io.audioshinigami.currencyconverter.repository.FlagDataRepository
import io.audioshinigami.currencyconverter.repository.RateRepository
import io.audioshinigami.currencyconverter.selectcurrency.CurrencySelectVMFactory
import io.audioshinigami.currencyconverter.selectcurrency.CurrencySelectViewModel


fun <T : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>): T {

    return when{
        viewModelClass.isAssignableFrom(CurrencyConvertViewModel::class.java) -> ViewModelProvider(this, setUpCurrencyConvertFactory()).get(viewModelClass)
        viewModelClass.isAssignableFrom(CurrencySelectViewModel::class.java) -> ViewModelProvider(this, setupCurrencySelectFactory() ).get(viewModelClass)
        else -> ViewModelProvider(this).get(viewModelClass)
    }
} // END obtainViewModel

private fun setUpCurrencyConvertFactory(): CurrencyConvertVMFactory {
    return CurrencyConvertVMFactory(FlagDataRepository(), RateRepository(ApiFactory.rateApi))
}

private fun setupCurrencySelectFactory(): CurrencySelectVMFactory {
    return CurrencySelectVMFactory( FlagDataRepository())
}
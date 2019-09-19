package io.audioshinigami.currencyconverter.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.audioshinigami.currencyconverter.App
import io.audioshinigami.currencyconverter.convertAmount.CurrencyConvertVMFactory
import io.audioshinigami.currencyconverter.network.ApiFactory
import io.audioshinigami.currencyconverter.repository.CodeRepository
import io.audioshinigami.currencyconverter.repository.RateRepository
import java.math.RoundingMode
import java.text.DecimalFormat

fun Map<String, String>.toApiString(): String {
    var result = ""

    for( (key,value) in this ){
        result += "&$key=$value"
    } /*end FOR*/

    return result.removePrefix("&")

} /*end toApiString*/

fun <T : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>): T {
    val codeRepository = CodeRepository(App.instance)
    val rateRepository = RateRepository(ApiFactory.rateApi)
    val viewModelFactory = CurrencyConvertVMFactory(codeRepository, rateRepository)
    return ViewModelProvider(this, viewModelFactory).get(viewModelClass)
}

val Double.currencyFormat: String
    get() = DecimalFormat("#,###.##").apply { roundingMode = RoundingMode.CEILING }.format(this)



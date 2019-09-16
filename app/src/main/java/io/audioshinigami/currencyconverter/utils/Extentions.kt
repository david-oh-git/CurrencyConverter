package io.audioshinigami.currencyconverter.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import io.audioshinigami.currencyconverter.viewmodels.CurrencyConvertVMFactory
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
    val viewModelFactory = CurrencyConvertVMFactory()
    return ViewModelProvider(this, viewModelFactory).get(viewModelClass)
}

val Double.currencyFormat: String
    get() = DecimalFormat("#,###.##").apply { roundingMode = RoundingMode.CEILING }.format(this)



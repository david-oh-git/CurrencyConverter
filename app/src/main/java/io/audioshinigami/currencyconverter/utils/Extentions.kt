package io.audioshinigami.currencyconverter.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.fragment.app.Fragment
import java.math.RoundingMode
import java.text.DecimalFormat

fun Map<String, String>.toApiString(): String {
    var result = ""

    for( (key,value) in this ){
        result += "&$key=$value"
    } /*end FOR*/

    return result.removePrefix("&")

} /*end toApiString*/

val Double.currencyFormat: String
    get() = DecimalFormat("#,###.##").apply { roundingMode = RoundingMode.CEILING }.format(this)

fun Fragment.isNetworkAvailable(): Boolean {

    val connManager =  this.context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return when{
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
            connManager.getNetworkCapabilities(connManager.activeNetwork).let {
                val wifiOn = it?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ?: false
                val mobileDataOn = it?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ?: false

                wifiOn || mobileDataOn
            }
        }
        Build.VERSION.SDK_INT < Build.VERSION_CODES.M -> {

            connManager.activeNetworkInfo?.let {
                val networkType = it.type
                val wifi = ( networkType == ConnectivityManager.TYPE_WIFI )
                val mobile = ( networkType == ConnectivityManager.TYPE_MOBILE )

                mobile || wifi
            } ?: false

        }
        else -> false
    } //end when
}



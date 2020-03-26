package io.audioshinigami.currencyconverter.utils.extentions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

// check if network is available
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

// sends a Snack message
fun Fragment.sendSnack(message: String){
    Snackbar.make(this.requireView(), message , Snackbar.LENGTH_SHORT).show()
}
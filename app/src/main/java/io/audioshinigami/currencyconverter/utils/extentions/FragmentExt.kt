/*
 * MIT License
 *
 * Copyright (c) 2020  David Osemwota
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.audioshinigami.currencyconverter.utils.extentions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_home.*

// check if network is available
fun Fragment.isNetworkAvailable(): Boolean {

    val connManager =  context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return when{
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
            connManager.getNetworkCapabilities(connManager.activeNetwork).let {
                val wifiOn = it?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ?: false
                val mobileDataOn = it?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ?: false

                wifiOn || mobileDataOn
            }
        }
        Build.VERSION.SDK_INT < Build.VERSION_CODES.M -> {

            val networkInfo: NetworkInfo? = connManager.activeNetworkInfo

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
    Snackbar.make(this.requireView(), message , Snackbar.LENGTH_SHORT)
        .apply {
            anchorView = bottom_navigation
            show()
        }
}
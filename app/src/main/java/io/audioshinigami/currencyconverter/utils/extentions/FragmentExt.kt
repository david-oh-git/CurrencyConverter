/*
 * Copyright 2020 Audio Shinigami Studio.
 *  All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
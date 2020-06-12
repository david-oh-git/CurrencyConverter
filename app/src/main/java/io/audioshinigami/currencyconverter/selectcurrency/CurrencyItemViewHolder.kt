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

package io.audioshinigami.currencyconverter.selectcurrency

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.audioshinigami.currencyconverter.R
import io.audioshinigami.currencyconverter.models.CurrencyItem

class CurrencyItemViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    fun bind(currencyItem: CurrencyItem, clickListener: (String) -> Unit ){

        val currencyCode = view.findViewById<TextView>(R.id.currency_code)
        val flagIcon = view.findViewById<ImageView>(R.id.currency_flag)

        currencyItem.apply {
            flagIcon.setImageResource(icon)
            currencyCode.text = code
            view.setOnClickListener { clickListener(code) }
        }


    }// end bind
}
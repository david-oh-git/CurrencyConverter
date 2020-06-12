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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.audioshinigami.currencyconverter.R
import io.audioshinigami.currencyconverter.models.CurrencyItem

class CurrencyItemAdaptor(private val onCurrencyItemClicked: (String) -> Unit): RecyclerView.Adapter<CurrencyItemViewHolder>() {

    private var _currencyItems: ArrayList<CurrencyItem> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyItemViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_paper, parent ,false)
        return CurrencyItemViewHolder(view)

    }

    override fun getItemCount(): Int = if(_currencyItems.isEmpty() ) 0 else _currencyItems.size

    override fun onBindViewHolder(holder: CurrencyItemViewHolder, position: Int) {

        _currencyItems.apply {
            if(isNotEmpty())
                holder.bind(this[position], onCurrencyItemClicked )
        }
    } //end onBindViewHolder

    fun addCurrencyItems(currencyItems: ArrayList<CurrencyItem>){
        _currencyItems = currencyItems
        notifyDataSetChanged()

    }//end addCurrency

}
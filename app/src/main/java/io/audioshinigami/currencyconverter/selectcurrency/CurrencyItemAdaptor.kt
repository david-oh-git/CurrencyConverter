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
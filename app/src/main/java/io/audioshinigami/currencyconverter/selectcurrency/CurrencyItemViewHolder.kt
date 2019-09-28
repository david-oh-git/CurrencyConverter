package io.audioshinigami.currencyconverter.selectcurrency

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.audioshinigami.currencyconverter.R
import io.audioshinigami.currencyconverter.models.CurrencyItem

class CurrencyItemViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val flagIcon = view.findViewById<ImageView>(R.id.currency_flag)
    private val currencyCode = view.findViewById<TextView>(R.id.currency_code)

    fun bind(currencyItem: CurrencyItem){
        
        currencyItem.apply {
            flagIcon.setImageResource(this.icon)
            currencyCode.text = currencyItem.code
        }
    }// end bind
}
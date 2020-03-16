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
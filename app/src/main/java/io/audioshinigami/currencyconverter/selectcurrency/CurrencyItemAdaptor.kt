package io.audioshinigami.currencyconverter.selectcurrency

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.audioshinigami.currencyconverter.R
import io.audioshinigami.currencyconverter.models.CurrencyItem

class CurrencyItemAdaptor: RecyclerView.Adapter<CurrencyItemViewHolder>() {

    private lateinit var currencyItems: ArrayList<CurrencyItem>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyItemViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.currency_item_rview, parent ,false)
        return CurrencyItemViewHolder(view)

    }

    override fun getItemCount(): Int = if(currencyItems.isEmpty() ) 0 else currencyItems.size

    override fun onBindViewHolder(holder: CurrencyItemViewHolder, position: Int) {

        currencyItems.apply {
            if(this.isNotEmpty())
                holder.bind(this[position])
        }
    }
}
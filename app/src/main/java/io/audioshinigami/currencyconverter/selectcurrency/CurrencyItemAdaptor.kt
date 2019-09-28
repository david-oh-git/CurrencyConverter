package io.audioshinigami.currencyconverter.selectcurrency

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.audioshinigami.currencyconverter.R
import io.audioshinigami.currencyconverter.models.CurrencyItem

class CurrencyItemAdaptor: RecyclerView.Adapter<CurrencyItemViewHolder>() {

    private var _currencyItems: ArrayList<CurrencyItem> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyItemViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.currency_item_rview, parent ,false)
        return CurrencyItemViewHolder(view)

    }

    override fun getItemCount(): Int = if(_currencyItems.isEmpty() ) 0 else _currencyItems.size

    override fun onBindViewHolder(holder: CurrencyItemViewHolder, position: Int) {

        _currencyItems.apply {
            if(this.isNotEmpty())
                holder.bind(this[position])
        }
    } //end onBindViewHolder

    fun addCurrencyItems(currencyItems: ArrayList<CurrencyItem>){
        _currencyItems = currencyItems
        notifyDataSetChanged()

    }//end addCurrency
}
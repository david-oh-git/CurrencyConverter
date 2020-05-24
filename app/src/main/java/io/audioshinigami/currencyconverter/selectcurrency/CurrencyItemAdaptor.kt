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
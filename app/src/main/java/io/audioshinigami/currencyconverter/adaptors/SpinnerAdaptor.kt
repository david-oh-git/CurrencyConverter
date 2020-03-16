package io.audioshinigami.currencyconverter.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import io.audioshinigami.currencyconverter.R
import io.audioshinigami.currencyconverter.models.CurrencyItem

class SpinnerAdaptor(val data: ArrayList<CurrencyItem>?): BaseAdapter() {

//    private val inflater: LayoutInflater = LayoutInflater.from(activity)

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {


        val holder: SpinnerItemHolder
        var row: View? = view


        if( view == null){
//            row = inflater.inflate(R.layout.currency_item_rview, parent, false)
            row = LayoutInflater.from(parent?.context).inflate(R.layout.currency_item_rview, parent, false)
            holder = SpinnerItemHolder(row)
            row.tag = holder
        }else{
            holder = view.tag as SpinnerItemHolder
        }

        val currency = data?.run { this[position] }
        holder.bind(currency!!)

        return row!!
    }

    override fun getItem(position: Int): Any {
        return data!![position]
    }

    override fun getItemId(p0: Int): Long = p0.toLong()


    override fun getCount(): Int = data!!.size

    class SpinnerItemHolder(var view: View?){

        var imageView: ImageView? = view?.findViewById(R.id.currency_flag)
        var textView: TextView? = view?.findViewById(R.id.currency_code)

        fun bind(currency: CurrencyItem){
            imageView?.setImageResource(currency.icon)
            textView?.text = currency.code
        }
    }
}
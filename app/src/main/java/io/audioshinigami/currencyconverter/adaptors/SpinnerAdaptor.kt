package io.audioshinigami.currencyconverter.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import io.audioshinigami.currencyconverter.R
import io.audioshinigami.currencyconverter.models.Currency

class SpinnerAdaptor(): BaseAdapter() {

    var data:ArrayList<Currency> = arrayListOf()

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {

        val row = view ?: LayoutInflater.from(parent?.context).inflate(R.layout.spinner_item, parent, false)

        val currency = data[position]

        val imageView: ImageView = row.findViewById(R.id.id_spinner_image)
        val textView: TextView = row.findViewById(R.id.id_spinner_text)
        
        textView.text = currency.name
        imageView.setImageResource(currency.icon)

        return row
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(p0: Int): Long = p0.toLong()


    override fun getCount(): Int = data.size
}
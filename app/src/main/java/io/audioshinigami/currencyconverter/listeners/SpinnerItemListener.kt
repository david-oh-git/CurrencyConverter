package io.audioshinigami.currencyconverter.listeners

import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import io.audioshinigami.currencyconverter.models.Currency

class SpinnerItemListener(var textView: TextView): AdapterView.OnItemSelectedListener {

    override fun onNothingSelected(p0: AdapterView<*>?) {
        // do nothing
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, p3: Long) {

        textView.text = Currency.getCountriesData()[position].name
    }
}
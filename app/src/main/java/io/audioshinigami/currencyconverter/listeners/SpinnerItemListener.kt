package io.audioshinigami.currencyconverter.listeners

import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import io.audioshinigami.currencyconverter.models.Currency
import io.audioshinigami.currencyconverter.models.FlagData

class SpinnerItemListener(private var textView: TextView? = null, val block: (Int) -> String): AdapterView.OnItemSelectedListener {

    override fun onNothingSelected(p0: AdapterView<*>?) {
        // do nothing
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, p3: Long) {

        textView?.text = block(position)
    }


}/*END*/
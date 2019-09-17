package io.audioshinigami.currencyconverter.convertAmount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.audioshinigami.currencyconverter.R
import io.audioshinigami.currencyconverter.utils.obtainViewModel

class CurrencyConvertFragment : Fragment() {

    companion object {
        fun newInstance() =
            CurrencyConvertFragment()
    }

    private lateinit var viewModel: CurrencyConvertViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.currency_convert_fragment, container, false)
        viewModel = obtainViewModel(CurrencyConvertViewModel::class.java)

        return view
    }

    private fun initSpinner(){
    }/*end initSpinner*/

}

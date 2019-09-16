package io.audioshinigami.currencyconverter.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.audioshinigami.currencyconverter.R
import io.audioshinigami.currencyconverter.utils.obtainViewModel
import io.audioshinigami.currencyconverter.viewmodels.CurrencyConvertViewModel

class CurrencyConvertFragment : Fragment() {

    companion object {
        fun newInstance() = CurrencyConvertFragment()
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CurrencyConvertViewModel::class.java)
        // TODO: Use the ViewModel
    }

}

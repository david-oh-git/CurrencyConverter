package io.audioshinigami.currencyconverter.convertAmount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.audioshinigami.currencyconverter.R
import io.audioshinigami.currencyconverter.databinding.CurrencyConvertFragmentBinding
import io.audioshinigami.currencyconverter.utils.obtainViewModel
import kotlinx.android.synthetic.main.currency_convert_fragment.*

class CurrencyConvertFragment : Fragment() {

    private lateinit var viewModel: CurrencyConvertViewModel
    private lateinit var viewDataBinding: CurrencyConvertFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.currency_convert_fragment, container, false)
        viewModel = obtainViewModel(CurrencyConvertViewModel::class.java)
        viewDataBinding = CurrencyConvertFragmentBinding.bind(root).apply {
            this.viewModel = viewModel
        }

        //Set lifecycler owner of view
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    private fun initSpinner(){
    }/*end initSpinner*/

    companion object {
        fun newInstance() =
            CurrencyConvertFragment()
    }

}

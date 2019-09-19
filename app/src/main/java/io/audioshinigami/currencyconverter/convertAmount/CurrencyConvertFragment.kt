package io.audioshinigami.currencyconverter.convertAmount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.audioshinigami.currencyconverter.R
import io.audioshinigami.currencyconverter.adaptors.SpinnerAdaptor
import io.audioshinigami.currencyconverter.databinding.CurrencyConvertFragmentBinding
import io.audioshinigami.currencyconverter.listeners.SpinnerItemListener
import io.audioshinigami.currencyconverter.repository.CodeRepository
import io.audioshinigami.currencyconverter.utils.obtainViewModel
import kotlinx.android.synthetic.main.currency_convert_fragment.*

/**
*  Main UI to covert amount . user enters value , selects currency codes and convert
*/

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
            this.vm = viewModel
        }

        //Set lifecycler owner of view
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // initialize both spinners
        initSpinner()
    }

    private fun initSpinner(){
        val adaptor = SpinnerAdaptor(CodeRepository().getAllCurrency())

        id_spinner_from.adapter = adaptor
        id_spinner_to.adapter = adaptor

        id_spinner_to.onItemSelectedListener = SpinnerItemListener(txtvw_currency_to){
                position -> CodeRepository().getCodes()[position]
        }
        id_spinner_from.onItemSelectedListener = SpinnerItemListener(txtvw_currency_from){
                position -> CodeRepository().getCodes()[position]
        }
    }/*end initSpinner*/

}

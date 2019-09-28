package io.audioshinigami.currencyconverter.convertAmount

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.audioshinigami.currencyconverter.R
import io.audioshinigami.currencyconverter.adaptors.SpinnerAdaptor
import io.audioshinigami.currencyconverter.convertAmount.events.ToastEvent
import io.audioshinigami.currencyconverter.databinding.CurrencyConvertFragmentBinding
import io.audioshinigami.currencyconverter.listeners.SpinnerItemListener
import io.audioshinigami.currencyconverter.repository.FlagDataRepository
import io.audioshinigami.currencyconverter.utils.isNetworkAvailable
import io.audioshinigami.currencyconverter.utils.obtainViewModel
import kotlinx.android.synthetic.main.currency_convert_fragment.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

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
        viewModel.networkAvailable = { isNetworkAvailable() }
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
        // click listener for textView
        initTextViewClicks()
    }

    private fun initSpinner(){
        val adaptor = SpinnerAdaptor(FlagDataRepository().getAllCurrency())

        id_spinner_from.adapter = adaptor
        id_spinner_to.adapter = adaptor

        id_spinner_to.onItemSelectedListener = SpinnerItemListener(txtvw_currency_to){
                position -> FlagDataRepository().getCodes()[position]
        }
        id_spinner_from.onItemSelectedListener = SpinnerItemListener(txtvw_currency_from){
                position -> FlagDataRepository().getCodes()[position]
        }
    }/*end initSpinner*/

    private fun initTextViewClicks(){

        txtvw_currency_from.setOnClickListener {
            findNavController().navigate(R.id.action_currencyConvertFragment_to_currencySelectFragment)
        }
    }

    @Subscribe
    fun sendToastMessage( toastEvent: ToastEvent){
        Toast.makeText(context, toastEvent.toastMessage, Toast.LENGTH_LONG).show()
    } // sendToastmsg

    override fun onAttach(context: Context) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }
}

package io.audioshinigami.currencyconverter.convertAmount

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import io.audioshinigami.currencyconverter.R
import io.audioshinigami.currencyconverter.adaptors.SpinnerAdaptor
import io.audioshinigami.currencyconverter.convertAmount.events.Event
import io.audioshinigami.currencyconverter.databinding.CurrencyConvertFragmentBinding
import io.audioshinigami.currencyconverter.listeners.SpinnerItemListener
import io.audioshinigami.currencyconverter.network.ApiFactory
import io.audioshinigami.currencyconverter.repository.FlagDataRepository
import io.audioshinigami.currencyconverter.repository.RateRepository
import io.audioshinigami.currencyconverter.sharedviewmodels.SharedCurrencyVMFactory
import io.audioshinigami.currencyconverter.sharedviewmodels.SharedCurrencyViewModel
import io.audioshinigami.currencyconverter.utils.FROM_CODE_KEY
import io.audioshinigami.currencyconverter.utils.TO_CODE_KEY
import io.audioshinigami.currencyconverter.utils.extentions.isNetworkAvailable
import kotlinx.android.synthetic.main.currency_convert_fragment.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
*  Main UI to covert amount . user enters value , selects currency codes and convert
*/

class CurrencyConvertFragment : Fragment() {

    private val testFactoryShared : SharedCurrencyVMFactory by lazy {
        SharedCurrencyVMFactory(
            FlagDataRepository(), RateRepository(
                ApiFactory.rateApi
            )
        )
    }
    private val viewModel: SharedCurrencyViewModel by activityViewModels { testFactoryShared }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.networkAvailable = { isNetworkAvailable() }
        val binding = CurrencyConvertFragmentBinding.inflate(inflater, container, false)
            .apply {
                vm = viewModel
                lifecycleOwner = this@CurrencyConvertFragment.viewLifecycleOwner
            }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

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
            startSelectCurrencyFragment(FROM_CODE_KEY)
        }

        txtvw_currency_to.setOnClickListener {
            startSelectCurrencyFragment(TO_CODE_KEY)
        }
    }

    @Subscribe
    fun sendToastMessage( toastEvent: Event.ToastEvent){
        Toast.makeText(context, toastEvent.message, Toast.LENGTH_LONG).show()
    } // sendToastmsg

    @Subscribe
    fun sendSnackMessage(snackBarEvent: Event.SnackBarEvent){
        Snackbar.make( convert_currency_layout , snackBarEvent.message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }

    private fun startSelectCurrencyFragment(code: String){
        val bundle = bundleOf( "key" to code)
        findNavController().navigate(R.id.action_currencyConvertFragment_to_currencySelectFragment, bundle)
    }
}

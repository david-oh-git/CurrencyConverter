package io.audioshinigami.currencyconverter.convertAmount

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import io.audioshinigami.currencyconverter.HomeActivity
import io.audioshinigami.currencyconverter.R
import io.audioshinigami.currencyconverter.adaptors.SpinnerAdaptor
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

/**
*  Main UI to covert amount . user enters value , selects currency codes and convert
*/

class CurrencyConvertFragment : Fragment() {

    private val viewModel: SharedCurrencyViewModel by activityViewModels {
        SharedCurrencyVMFactory(
            FlagDataRepository(), RateRepository(
                ApiFactory.rateApi
            )
        )
    }

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

        subscribeData(binding)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initTextViewClicks()
    }

    private fun subscribeData( binding: CurrencyConvertFragmentBinding){

        binding.lifecycleOwner
            ?.run {
                viewModel.snackMessage.observe(this, Observer {
                    hideKeyboard(edittxt_currency_from)
                    ( activity as HomeActivity ).sendSnackBar(it.message)
                })
            }

//        viewModel.snackMessage.observe(binding.lifecycleOwner!!, Observer {
//            hideKeyboard(edittxt_currency_from)
//            ( activity as HomeActivity ).sendSnackBar(it.message)
//        })
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

    private fun startSelectCurrencyFragment(code: String){
        val bundle = bundleOf( "key" to code)
        findNavController().navigate(R.id.action_currencyConvertFragment_to_currencySelectFragment, bundle)
    }

    private fun hideKeyboard(view: View){
        (context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager )
            .apply {
                hideSoftInputFromWindow(view.windowToken, 0)
            }

    }
}

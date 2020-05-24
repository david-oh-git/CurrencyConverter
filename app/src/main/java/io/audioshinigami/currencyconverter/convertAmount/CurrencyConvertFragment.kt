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
import io.audioshinigami.currencyconverter.R
import io.audioshinigami.currencyconverter.databinding.CurrencyConvertFragmentBinding
import io.audioshinigami.currencyconverter.home.HomeActivity
import io.audioshinigami.currencyconverter.network.ApiFactory
import io.audioshinigami.currencyconverter.repository.FlagDataRepository
import io.audioshinigami.currencyconverter.repository.RateRepository
import io.audioshinigami.currencyconverter.sharedviewmodels.SharedCurrencyVMFactory
import io.audioshinigami.currencyconverter.sharedviewmodels.SharedCurrencyViewModel
import io.audioshinigami.currencyconverter.utils.FROM_CODE_KEY
import io.audioshinigami.currencyconverter.utils.TO_CODE_KEY
import io.audioshinigami.currencyconverter.utils.extentions.isNetworkAvailable

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

    private lateinit var binding: CurrencyConvertFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.networkAvailable = { isNetworkAvailable() }
        binding = CurrencyConvertFragmentBinding.inflate(inflater, container, false)
            .apply {
                vm = viewModel
                lifecycleOwner = this@CurrencyConvertFragment.viewLifecycleOwner

            }

        subscribeData()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initTextViewClicks()
    }

    private fun subscribeData(){

        binding.lifecycleOwner
            ?.run {
                viewModel.snackMessage.observe(this, Observer {
                    hideKeyboard(binding.edittxtCurrencyFrom)
                    ( activity as HomeActivity).sendSnackBar(it.message)
                })
            }

//        viewModel.snackMessage.observe(binding.lifecycleOwner!!, Observer {
//            hideKeyboard(edittxt_currency_from)
//            ( activity as HomeActivity ).sendSnackBar(it.message)
//        })
    }

    private fun initTextViewClicks(){

        binding.txtvwCurrencyFrom.setOnClickListener {
            startSelectCurrencyFragment(FROM_CODE_KEY)
        }

        binding.txtvwCurrencyTo.setOnClickListener {
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

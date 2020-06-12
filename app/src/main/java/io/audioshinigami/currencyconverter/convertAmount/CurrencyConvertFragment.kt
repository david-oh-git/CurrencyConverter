/*
 * Copyright 2020 Audio Shinigami Studio.
 *  All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import io.audioshinigami.currencyconverter.repository.RateRepository
import io.audioshinigami.currencyconverter.sharedviewmodels.SharedCurrencyVMFactory
import io.audioshinigami.currencyconverter.sharedviewmodels.SharedCurrencyViewModel
import io.audioshinigami.currencyconverter.utils.FRAGMENT_CODE
import io.audioshinigami.currencyconverter.utils.FROM_CODE_KEY
import io.audioshinigami.currencyconverter.utils.TO_CODE_KEY
import io.audioshinigami.currencyconverter.utils.extentions.isNetworkAvailable

/**
*  Main UI to covert amount . user enters value , selects currency codes and convert
*/

class CurrencyConvertFragment : Fragment() {

    private val viewModel: SharedCurrencyViewModel by activityViewModels {
        SharedCurrencyVMFactory( RateRepository(
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
                lifecycleOwner = viewLifecycleOwner

            }

        subscribeData()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initTextViewClicks()
    }

    private fun subscribeData(){


        viewModel.snackMessage.observe(viewLifecycleOwner, Observer {
            hideKeyboard(binding.edittxtCurrencyFrom)
            ( activity as HomeActivity).sendSnackBar(it.message)
        })
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
        val bundle = bundleOf( FRAGMENT_CODE to code)
        findNavController().navigate(R.id.action_currencyConvertFragment_to_currencySelectFragment, bundle)
    }

    private fun hideKeyboard(view: View){
        (context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager )
            .apply {
                hideSoftInputFromWindow(view.windowToken, 0)
            }

    }
}

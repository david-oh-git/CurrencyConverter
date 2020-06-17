/*
 * MIT License
 *
 * Copyright (c) 2020  David Osemwota
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.audioshinigami.currencyconverter.convertAmount

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import io.audioshinigami.currencyconverter.App
import io.audioshinigami.currencyconverter.R
import io.audioshinigami.currencyconverter.databinding.CurrencyConvertFragmentBinding
import io.audioshinigami.currencyconverter.home.HomeActivity
import io.audioshinigami.currencyconverter.utils.FRAGMENT_CODE
import io.audioshinigami.currencyconverter.utils.network.InternetCallback
import io.audioshinigami.currencyconverter.utils.network.NetworkChecker
import timber.log.Timber
import javax.inject.Inject

/**
*  Main UI to covert amount . user enters value , selects currency codes and convert
*/

class CurrencyConvertFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<ConvertViewModel> { viewModelFactory }

    private var callback: InternetCallback? = null

    private lateinit var binding: CurrencyConvertFragmentBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        ( requireActivity().application as App).appComponent.convertComponent().create()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            initInternetCallback()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = CurrencyConvertFragmentBinding.inflate(inflater, container, false)
            .apply {
                vm = viewModel
                launchSelectFrag = ::startSelectCurrencyFragment
                lifecycleOwner = viewLifecycleOwner

            }

        subscribeData()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            callback?.let {
                NetworkChecker.registerInternetAvailabilityCallback(requireContext(), it)
                Timber.d("Network registered")
            }
        }
    }

    override fun onPause() {
        super.onPause()

        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            callback?.let {
                NetworkChecker.unRegisterInternetAvailabilityCallback(requireContext(), it)
            }
        }
    }

    private fun initInternetCallback(){

        callback = object: InternetCallback() {
            override fun onNetworkInActive() {
                Timber.d("No Internet")
                viewModel.setHasNetworkConnection(false)
            }

            override fun onNetworkActive() {
                Timber.d("Yes Internet")
                viewModel.setHasNetworkConnection(true)
            }
        }
    }

    private fun subscribeData(){


        viewModel.snackMessage.observe(viewLifecycleOwner, Observer {
            hideKeyboard(binding.edittxtCurrencyFrom)
            ( activity as HomeActivity).sendSnackBar(it.message)
        })
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

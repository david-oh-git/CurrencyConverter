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

package io.audioshinigami.currencyconverter.selectcurrency

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import io.audioshinigami.currencyconverter.App
import io.audioshinigami.currencyconverter.data.source.local.PaperDao
import io.audioshinigami.currencyconverter.databinding.CurrencySelectFragmentBinding
import io.audioshinigami.currencyconverter.network.ApiFactory
import io.audioshinigami.currencyconverter.repository.RateRepository
import io.audioshinigami.currencyconverter.sharedviewmodels.SharedCurrencyVMFactory
import io.audioshinigami.currencyconverter.sharedviewmodels.SharedCurrencyViewModel
import io.audioshinigami.currencyconverter.utils.FRAGMENT_CODE
import io.audioshinigami.currencyconverter.utils.FROM_CODE_KEY
import io.audioshinigami.currencyconverter.utils.TO_CODE_KEY
import timber.log.Timber
import javax.inject.Inject

class CurrencySelectFragment : DialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<PaperViewModel> { viewModelFactory }
    private lateinit var binding: CurrencySelectFragmentBinding
    private val adaptor: PaperAdaptor by lazy {
        PaperAdaptor(::setCode)
    }

    // code value , if fragment is to select a FROM code or TO code
    private var callingFragmentCode: String = ""

    @Inject
    lateinit var paperDao: PaperDao

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as App).appComponent.paperComponent().create()
            .inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CurrencySelectFragmentBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                viewmodel = viewModel
            }


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // init recyclerView adaptor
        setupRecyclerView()

        arguments?.also { it ->
            val fragmentCode = it.getString(FRAGMENT_CODE)
            fragmentCode?.let {code ->
                callingFragmentCode = code
                Timber.d("fragment calling code is $callingFragmentCode")
            }


        }
        binding.viewmodel?.items?.observe(viewLifecycleOwner, Observer {
            Timber.d("Paper data size is ${it.size}")
            adaptor.submitList(it)
        })


    }

    private fun setupRecyclerView(){

        val viewModel = binding.viewmodel
        viewModel?.let {
            binding.currencyItemRecyclerview.adapter = adaptor
        }

        // TODO save state abi remember position
//        currencyItem_recyclerview.adapter = adaptor
//        currencyItem_recyclerview.layoutManager = GridLayoutManager(activity, 3)
//        currencyItem_recyclerview.hasFixedSize()
    }

    private fun setCode(code: String){
        // TODO replace this hack
        // init ShareViewModel to assign the currency Code to [CurrencyConvertFragment]
        val factoryShared : SharedCurrencyVMFactory by lazy {
            SharedCurrencyVMFactory(RateRepository(
                    ApiFactory.rateApi
                )
            )
        }
        val viewModelShared: SharedCurrencyViewModel by activityViewModels { factoryShared }

        if( callingFragmentCode == FROM_CODE_KEY)
            viewModelShared.setFromCode(code)

        if( callingFragmentCode == TO_CODE_KEY)
            viewModelShared.setToCode(code)

        findNavController().popBackStack()
    }

}

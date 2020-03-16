package io.audioshinigami.currencyconverter.selectcurrency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import io.audioshinigami.currencyconverter.R
import io.audioshinigami.currencyconverter.sharedviewmodels.SharedCurrencyVMFactory
import io.audioshinigami.currencyconverter.sharedviewmodels.SharedCurrencyViewModel
import io.audioshinigami.currencyconverter.network.ApiFactory
import io.audioshinigami.currencyconverter.repository.FlagDataRepository
import io.audioshinigami.currencyconverter.repository.RateRepository
import io.audioshinigami.currencyconverter.utils.FROM_CODE_KEY
import io.audioshinigami.currencyconverter.utils.TO_CODE_KEY
import io.audioshinigami.currencyconverter.utils.obtainViewModel
import kotlinx.android.synthetic.main.currency_select_fragment.*

class CurrencySelectFragment : DialogFragment() {

    private lateinit var viewModel: CurrencySelectViewModel
    private val adaptor: CurrencyItemAdaptor by lazy{ CurrencyItemAdaptor{
            code ->
            onItemClicked(code)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.currency_select_fragment, container, false)
        viewModel = obtainViewModel(CurrencySelectViewModel::class.java)
        //load currency items wit coroutines
        viewModel.loadCurrencyItems()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // init recylerView
        setupRecyclerView()
        // assign currencyItems to recyclerView adaptor

        viewModel.currencyItems.observe(viewLifecycleOwner, Observer {
            adaptor.addCurrencyItems(it)
        })

        arguments?.also {
            val key = it.getString(FROM_CODE_KEY)
        }


    }

    private fun setupRecyclerView(){

        // TODO save state abi remember position
        currencyItem_recyclerview.adapter = adaptor
        currencyItem_recyclerview.layoutManager = GridLayoutManager(activity, 3)
        currencyItem_recyclerview.hasFixedSize()
    }

    private fun onItemClicked(code: String){
        // Restarts navigation start fragment with args
        val callerCode: String
        arguments?.apply {
            callerCode = getString("key") ?: "0"
            setCode(callerCode, code)
        }

        findNavController().popBackStack()
    } //END onItemClick

    private fun setCode(callerCode: String,code: String){
        // init ShareViewModel to assign the currency Code to [CurrencyConvertFragment]
        val testFactoryShared : SharedCurrencyVMFactory by lazy {
            SharedCurrencyVMFactory(
                FlagDataRepository(), RateRepository(
                    ApiFactory.rateApi
                )
            )
        }
        val viewModelShared: SharedCurrencyViewModel by activityViewModels { testFactoryShared }

        if( callerCode == FROM_CODE_KEY)
            viewModelShared.setFromCode(code)

        if( callerCode == TO_CODE_KEY)
            viewModelShared.setToCode(code)
    }

}

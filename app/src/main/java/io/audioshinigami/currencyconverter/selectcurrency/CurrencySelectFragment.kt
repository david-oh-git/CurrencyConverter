package io.audioshinigami.currencyconverter.selectcurrency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import io.audioshinigami.currencyconverter.R
import io.audioshinigami.currencyconverter.utils.obtainViewModel
import kotlinx.android.synthetic.main.currency_select_fragment.*

class CurrencySelectFragment : DialogFragment() {

    private lateinit var viewModel: CurrencySelectViewModel
    private val adaptor: CurrencyItemAdaptor by lazy { CurrencyItemAdaptor() }

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
        // assign currencyItems to recyelerview adaptor

        viewModel.currencyItems.observe(viewLifecycleOwner, Observer {
            adaptor.addCurrencyItems(it)
        })

    }

    private fun setupRecyclerView(){
        currencyItem_recyclerview.adapter = adaptor
        currencyItem_recyclerview.layoutManager = GridLayoutManager(activity, 3)
    }

}

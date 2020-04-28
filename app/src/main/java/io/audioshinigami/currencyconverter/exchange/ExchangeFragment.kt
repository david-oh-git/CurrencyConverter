package io.audioshinigami.currencyconverter.exchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import io.audioshinigami.currencyconverter.App
import io.audioshinigami.currencyconverter.R
import io.audioshinigami.currencyconverter.data.DatabaseSource
import io.audioshinigami.currencyconverter.databinding.ExchangeFragmentBinding
import io.audioshinigami.currencyconverter.utils.DATASOURCE_REMOTE
import javax.inject.Inject
import javax.inject.Named

class ExchangeFragment : Fragment() {

    @Inject @field:Named(DATASOURCE_REMOTE) lateinit var databaseSource: DatabaseSource

    private val viewModel by viewModels<ExchangeViewModel> {
        ExchangeViewModelFactory( databaseSource )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = ExchangeFragmentBinding.inflate( inflater, container, false)
            .apply {
                lifecycleOwner = this@ExchangeFragment.viewLifecycleOwner
                vm = viewModel
            }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ( requireContext().applicationContext as App).appComponent.inject(this)
    }


}

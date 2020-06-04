package io.audioshinigami.currencyconverter.exchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.audioshinigami.currencyconverter.databinding.ExchangeFragmentBinding

class ExchangeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = ExchangeFragmentBinding.inflate( inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                // TODO add viewModel
            }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


}

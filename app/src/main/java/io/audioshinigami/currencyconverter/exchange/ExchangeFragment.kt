package io.audioshinigami.currencyconverter.exchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.audioshinigami.currencyconverter.R
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class ExchangeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.exchange_fragment, container, false)
    }

    override fun onResume() {
        super.onResume()

        dateNow()
    }
    private fun dateNow(){
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val formattedDate = formatter.format(time)
        Timber.d("The time is : $formattedDate")
    }
}

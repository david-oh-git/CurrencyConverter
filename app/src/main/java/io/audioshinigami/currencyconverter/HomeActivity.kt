package io.audioshinigami.currencyconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.audioshinigami.currencyconverter.adaptors.SpinnerAdaptor
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpSpinner()
    }


    private fun setUpSpinner(){
        val adaptor = SpinnerAdaptor()
        id_spiner_to.adapter = adaptor
        id_spinner_from.adapter = adaptor
    } /*end setupSpinner*/
}

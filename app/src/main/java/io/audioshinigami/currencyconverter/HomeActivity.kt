package io.audioshinigami.currencyconverter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.audioshinigami.currencyconverter.adaptors.SpinnerAdaptor
import kotlinx.android.synthetic.main.content_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setUpSpinner()
    }


    private fun setUpSpinner(){
        val adaptor = SpinnerAdaptor()
        id_spinner_to.adapter = adaptor
        id_spinner_from.adapter = adaptor
    } /*end setupSpinner*/
}

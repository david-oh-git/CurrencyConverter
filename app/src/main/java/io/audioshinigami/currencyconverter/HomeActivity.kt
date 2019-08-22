package io.audioshinigami.currencyconverter

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import io.audioshinigami.currencyconverter.adaptors.SpinnerAdaptor
import io.audioshinigami.currencyconverter.models.Currency
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*
import androidx.lifecycle.ViewModelProviders
import io.audioshinigami.currencyconverter.viewmodels.CurrencyViewmodel

class HomeActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this).get(CurrencyViewmodel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)

        setUpSpinner()

        id_btn_convert.setOnClickListener{
            convertCurrency()
        }
    }

    private fun convertCurrency() {
        val fromCurrency = Currency.getCountriesData()[id_spinner_from.selectedItemPosition].name
        val toCurrency = Currency.getCountriesData()[id_spinner_to.selectedItemPosition].name

        Log.d("TAGU", "cCode is : $fromCurrency")
        viewModel.getRate("${fromCurrency}_$toCurrency")
        viewModel.rateLiveData.observe(this, Observer { data ->
            id_txtvw_test_result.text = data.toString()
        })



    }

    private fun setUpSpinner(){
        val adaptor = SpinnerAdaptor()
        id_spinner_to.adapter = adaptor
        id_spinner_from.adapter = adaptor
    } /*end setupSpinner*/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    } /*end onCreateOptions*/

    private fun setTestView(data: String){
        id_txtvw_test_result.text = data
    }

} /*end END*/

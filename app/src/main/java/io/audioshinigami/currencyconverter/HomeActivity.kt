package io.audioshinigami.currencyconverter

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.audioshinigami.currencyconverter.adaptors.SpinnerAdaptor
import io.audioshinigami.currencyconverter.listeners.SpinnerItemListener
import io.audioshinigami.currencyconverter.models.Currency
import io.audioshinigami.currencyconverter.viewmodels.CurrencyViewmodel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*

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
        if(!isFormValid())
            return

        val fromCurrency = Currency.getCountriesData()[id_spinner_from.selectedItemPosition].name
        val toCurrency = Currency.getCountriesData()[id_spinner_to.selectedItemPosition].name
        val currencyCode = "${fromCurrency}_$toCurrency,${toCurrency}_$fromCurrency"
        var result: Map<String, String>
        viewModel.getRate(currencyCode)
        viewModel.rateLiveData.observe(this, Observer { data ->
            result = data.rate
            id_txtvw_test_result.text = result.toString()
        })

    }

    private fun setUpSpinner(){
        val adaptor = SpinnerAdaptor()
        id_spinner_from.adapter = adaptor
        id_spinner_to.adapter = adaptor


        id_spinner_to.onItemSelectedListener = SpinnerItemListener(txtvw_currency_to)
        id_spinner_from.onItemSelectedListener = SpinnerItemListener(txtvw_currency_from)
    } /*end setupSpinner*/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    } /*end onCreateOptions*/

    private fun isFormValid(): Boolean {
        var valid = true

        val fromCurrency = edittxt_currency_from.text
        val toCurrency = edittxt_currenct_to.text

        if( fromCurrency.isNullOrEmpty()){
            edittxt_currency_from.error = "Required."
            edittxt_currency_from.requestFocus()
            valid = false
        }

        if( toCurrency.isNullOrEmpty()){
            edittxt_currenct_to.error = "Required."
            edittxt_currenct_to.requestFocus()
            valid = false
        }

        return valid
    } /*end isFormValid*/
} /*end END*/

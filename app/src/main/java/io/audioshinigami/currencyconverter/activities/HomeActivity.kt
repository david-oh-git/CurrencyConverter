package io.audioshinigami.currencyconverter.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import io.audioshinigami.currencyconverter.R
import io.audioshinigami.currencyconverter.adaptors.SpinnerAdaptor
import io.audioshinigami.currencyconverter.listeners.SpinnerItemListener
import io.audioshinigami.currencyconverter.models.FlagData
import io.audioshinigami.currencyconverter.utils.currencyFormat
import io.audioshinigami.currencyconverter.viewmodels.CurrencyViewModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*

class HomeActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this).get(CurrencyViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)

        /*removes title from actionBar*/
        supportActionBar?.let { it.title = "" }

        setUpSpinner()

        id_btn_convert.setOnClickListener{
            convertCurrency()
//            slideUp()
        }

    }

    private fun convertCurrency() {
        if(!isFormValid())
            return

        val fromCurrency = viewModel.getCode(id_spinner_from.selectedItemPosition)
        val toCurrency = viewModel.getCode(id_spinner_to.selectedItemPosition)
        val currencyCode = "${fromCurrency}_$toCurrency,${toCurrency}_$fromCurrency"
        var result: Map<String, String>

        /*get currency rate from API*/
        viewModel.fetchRate(currencyCode)
        viewModel.rateLiveData.observe(this, Observer { data ->
            result = data.rate
            calculateCurrency( "${fromCurrency}_$toCurrency", result)
        })

    }

    private fun calculateCurrency(code: String, result: Map<String, String>) {
        val rate = viewModel.extractRate(code, result)

        val currencyValue = edittxt_currency_from.text.toString().toDouble()
        val convertedValue = currencyValue * rate.rateValue

        edittxt_currency_to.setText(convertedValue.currencyFormat)

        edittxt_currency_to.setTextColor(ContextCompat.getColor(this,
            R.color.project_blue
        ))
    }

    private fun setUpSpinner(){
        val flagTest = viewModel.flags.size
        Log.d("TAGU", "flag count is : $flagTest")
        val adaptor = SpinnerAdaptor(this, viewModel.getAllCurrency())

        id_spinner_from.adapter = adaptor
        id_spinner_to.adapter = adaptor

        id_spinner_to.onItemSelectedListener = SpinnerItemListener(txtvw_currency_to){
                position -> viewModel.getCode(position)
        }
        id_spinner_from.onItemSelectedListener = SpinnerItemListener(txtvw_currency_from){
            position -> viewModel.getCode(position)
        }

    } /*end setupSpinner*/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    } /*end onCreateOptions*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if( item.itemId == R.id.action_sign_up){
            Snackbar.make( parent_layout , "To be implemented soon :)", Snackbar.LENGTH_LONG).show()
        }

        return true
    }

    private fun isFormValid(): Boolean {
        var valid = true

        val fromCurrency = edittxt_currency_from.text

        if( fromCurrency.isNullOrEmpty()){
            edittxt_currency_from.error = "Required."
            edittxt_currency_from.requestFocus()
            valid = false
        }

        return valid
    } /*end isFormValid*/

    private fun slideUp(){
        val slideUpAnimation = AnimationUtils.loadAnimation(this,
            R.anim.slide_up
        )
//        graph_layout.animation = slideUpAnimation
        graph.bringToFront()
        graph.animation = slideUpAnimation
    } /*end slide*/

} /*end END*/

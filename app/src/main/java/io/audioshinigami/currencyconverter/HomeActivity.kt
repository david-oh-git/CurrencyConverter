package io.audioshinigami.currencyconverter

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import io.audioshinigami.currencyconverter.adaptors.SpinnerAdaptor
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)

        setUpSpinner()
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

} /*end END*/

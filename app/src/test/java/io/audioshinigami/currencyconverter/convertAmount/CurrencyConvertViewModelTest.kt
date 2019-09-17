package io.audioshinigami.currencyconverter.convertAmount

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CurrencyConvertViewModelTest {

    // Test subject
    private lateinit var viewModelTest: CurrencyConvertViewModel
    // Execute each task synchronously
    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initTest(){
        viewModelTest = CurrencyConvertViewModel()
    } /*end initTest*/

    @Test
    fun convertCurrency_

} /*END*/
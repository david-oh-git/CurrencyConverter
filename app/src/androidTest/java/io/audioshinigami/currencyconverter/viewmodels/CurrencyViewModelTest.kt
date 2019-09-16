package io.audioshinigami.currencyconverter.viewmodels

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

class CurrencyViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val application = Application()
    private lateinit var viewModel: CurrencyViewModel

    @Before
    fun setUp(){
        viewModel = CurrencyViewModel(application)
    }

    @Test
    fun getRequestCodeCorrectCode() {
        val fromCode = "NGN"
        val toCode = "USD"
        //USD_PHP,PHP_USD

        assertEquals("${fromCode}_$toCode,${toCode}_$fromCode", viewModel.getRequestCode(fromCode, toCode))
    }
}
package io.audioshinigami.currencyconverter.convertAmount

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert
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
    fun getRequestCode_correctCode(){
        //Arrange
        val fromCode = "NGN"
        val toCode = "USD"
        //Act
        val code = viewModelTest.getRequestCode(fromCode, toCode)

        //Assert

        Assert.assertEquals(
            "${fromCode}_$toCode,${toCode}_$fromCode",
            code
        )
    }

    @Test
    fun getRequestCode_invalidCode(){
        //Arrange
        val fromCode = "NGN"
        val toCode = "USD"
        //Act
        val code = viewModelTest.getRequestCode(fromCode, toCode)

        //Assert)
    }

} /*END*/
package io.audioshinigami.currencyconverter.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.audioshinigami.currencyconverter.App
import io.audioshinigami.currencyconverter.utils.currency_codes_total
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito

class CodeRepositoryTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var codeRepository: CodeRepository
    private lateinit var appMock: App


    @Before
    fun initTest(){
        appMock = Mockito.mock(App::class.java)
        codeRepository = CodeRepository(appMock)
    }

    @Test
    fun getAllCurrency_countTotal() {
        //Arrange
        val currencyItemsTotal = currency_codes_total

        //Act
        val result = codeRepository.getAllCurrency().size

        //Assert
        assertEquals(currencyItemsTotal, result)
    }

    @Test
    fun getFlags_countTotal() {
        //Arrange
        val flagsCodesTotal = currency_codes_total

        //Act
        val result = codeRepository.getFlags().size

        //Assert
        assertEquals(flagsCodesTotal, result)
    }

    @Test
    fun geCodes_countTotal() {
        //Arrange
        val currencyCodesTotal = currency_codes_total

        //Act
        val result = codeRepository.getCodes().size

        //Assert
        assertEquals(currencyCodesTotal, result)
    }
}
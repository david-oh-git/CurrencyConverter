package io.audioshinigami.currencyconverter.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.audioshinigami.currencyconverter.App
import io.audioshinigami.currencyconverter.models.CurrencyItem
import io.audioshinigami.currencyconverter.utils.currency_codes_total
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import kotlin.random.Random


class CodeRepositoryTestShared {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var codeRepository: CodeRepository
    private lateinit var app: App


    @Before
    fun initTest(){
        app = App.instance
        codeRepository = CodeRepository(app)
    }

    @Test
    fun getAllCurrencyItem_countTotal() {
        //Arrange
        val currencyItemsTotal = currency_codes_total

        //Act
        val result = codeRepository.getAllCurrency().size

        //Assert
        assertEquals(currencyItemsTotal, result)
    }

    @Test
    fun getAllCurrencyItem_positionCorrect(){
        //Arrange
        val position = Random.nextInt(currency_codes_total) // randomly generates position 0 - 165
        val codes = codeRepository.getCodes()
        val flags = codeRepository.getFlags()
        val testItem = CurrencyItem(codes[position], flags[position])

        //Act
        val allItems = codeRepository.getAllCurrency()
        val expectedItem = allItems[position]

        assertEquals(expectedItem, testItem)
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
    fun getCodes_countTotal() {
        //Arrange
        val currencyCodesTotal = currency_codes_total

        //Act
        val result = codeRepository.getCodes().size

        //Assert
        assertEquals(result, currencyCodesTotal)
    }

    @Test
    fun getCodes_countTotal_error(){
        //Arrange
        val currencyCodesTotalWrong = 42

        //Act
        val result = codeRepository.getCodes().size

        //Assert
        assertNotEquals(result, currencyCodesTotalWrong)
    }

    @Test
    fun getCodes_containsCode() {
        // Test if certain currency codes are present
        //Arrange  ... list of verified codes that it should contain
        // get random code from list
        val codes = arrayListOf<String>("NGN", "AUD", "CNY", "RWF", "LAK", "IRR", "ALL", "ANG")
        val code = codes.let { it[Random.nextInt(it.size)] }

        //Act
        val result = codeRepository.getCodes()

        //Assert
        assertEquals(true, codes.contains(code))
    }
} /*END*/
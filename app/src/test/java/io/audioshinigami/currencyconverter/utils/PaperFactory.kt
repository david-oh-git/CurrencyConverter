package io.audioshinigami.currencyconverter.utils

import io.audioshinigami.currencyconverter.data.Paper
import kotlin.random.Random

/**
 * create [Paper] data for testing
 */

object PaperFactory {

    private val codes = mapOf(
        "Albanian Lek" to "ALL",
        "Australian Dollar" to "AUD",
        "Nigerian Naira" to "NGN",
        "Chilean Peso" to "CLP",
        "US Dollars" to "USD"
    )

    fun getPaperEntries() = codes.map { Paper( randomId(), it.key, it.value) }

    fun getPaper() = getPaperEntries().shuffled()[0]

    private fun randomId() = Random.nextInt(1,11)
}
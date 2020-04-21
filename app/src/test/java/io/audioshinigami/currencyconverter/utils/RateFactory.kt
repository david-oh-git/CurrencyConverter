package io.audioshinigami.currencyconverter.utils

import io.audioshinigami.currencyconverter.data.Rate
import kotlin.random.Random

/**
 * To auto create [Rate] data for testing
 */

object RateFactory {

    val rates = listOf(
        Rate(randomId(),"NGN_USD", randomRate(), Date.currentDate),
        Rate(randomId(),"NGN_AUD", randomRate(), Date.currentDate),
        Rate(randomId(),"JYP_EUR", randomRate(), Date.currentDate),
        Rate(randomId(),"NZD_EUR", randomRate(), Date.currentDate),
        Rate(randomId(),"NZD_RWF", randomRate(), Date.currentDate)
    )

    val rate: Rate
        get() = rates.shuffled()[0]

    private fun randomRate() = Random.nextDouble(0.0, 1.0)

    private fun randomId() = Random.nextInt(1,11)
}
/*
 * Copyright 2020 Audio Shinigami Studio.
 *  All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
        get() = Rate(randomId(), "AUD_NGN", randomRate(), Date.currentDate)

    private fun randomRate() = Random.nextDouble(0.0, 1.0)

    private fun randomId() = Random.nextInt(1,11)
}
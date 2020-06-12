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

    fun getPaperEntries() = codes.map { Paper( randomId(), it.key, it.value, "","", "") }

    fun getPaper() = getPaperEntries().shuffled()[0]

    private fun randomId() = Random.nextInt(1,11)
}
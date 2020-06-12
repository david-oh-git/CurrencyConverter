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

package io.audioshinigami.currencyconverter.repository

import io.audioshinigami.currencyconverter.models.Rate
import io.audioshinigami.currencyconverter.models.RateResponse
import io.audioshinigami.currencyconverter.network.ConverterApi

class RateRepository(private val api: ConverterApi): BaseRepository() {

    suspend fun fetchRateFromApi(currencyCode: String): RateResponse? {

        return safeApiCall(
            call = { api.getRate(currencyCode = currencyCode).await() },
            errorMsg = "Error fetching currency rate"
        )

    }

    fun getRate(code: String, result: Map<String, String>): Rate {

        /*extract data gotten from API & assigns to Rate class*/
        val rate = Rate(code)

        for( (k,v) in  result ){
            if( k == code )
                rate.rateValue = v.toDouble()
            else
                rate.reverseRate = v.toDouble()
        } /*end FOR*/

        return rate
    }
}
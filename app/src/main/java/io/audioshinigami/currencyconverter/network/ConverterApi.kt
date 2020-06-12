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

package io.audioshinigami.currencyconverter.network

import io.audioshinigami.currencyconverter.models.RateResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ConverterApi {

    @GET("/api/v7/convert")
    fun getRate(
        @Query("q") currencyCode: String,
        @Query("compact") compact: String = "ultra"): Deferred<Response<RateResponse>>

    @GET("/api/v7/convert")
    fun getRates(
        @Query("q") currencyCode: String,
        @Query("compact") compact: String = "ultra"): Deferred<Response<io.audioshinigami.currencyconverter.network.RateResponse>>

    @GET("/api/v7/countries")
    fun getCurrencies(): Deferred<Response<PaperResponse>>
}
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

import io.audioshinigami.currencyconverter.network.Result
import retrofit2.Response
import timber.log.Timber

open class BaseRepository {

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMsg: String): T? {

        val result: Result<T> = safeApiResult(call, errorMsg)
        var data: T? = null

        when(result){
            is Result.Success -> data = result.data

            is Result.Error -> {
                Timber.d("Error !!! : $errorMsg")
            }
        } /* end when*/

        return data
    } /*end safeApi*/

    private suspend fun <T: Any> safeApiResult(call: suspend () -> Response<T>, errorMsg: String): Result<T>{
        val response =  call.invoke()
        if(response.isSuccessful) return Result.Success(response.body()!!)

        return Result.Error(Exception("Error occurred while getting safe api result, ERROR - $errorMsg"))
    } /*end safeApiResult*/
}
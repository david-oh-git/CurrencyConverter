/*
 * MIT License
 *
 * Copyright (c) 2020  David Osemwota
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.audioshinigami.currencyconverter.data.source.remote

import io.audioshinigami.currencyconverter.network.Result
import retrofit2.Response
import timber.log.Timber

open class BaseRemoteDataSource {

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
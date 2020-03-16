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
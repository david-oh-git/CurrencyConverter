package io.audioshinigami.currencyconverter.data

import androidx.lifecycle.LiveData

interface AppRepository {

    suspend fun save( key: String , value: String )

    suspend fun save( key: String , value: Boolean )

    suspend fun save( key: String , value: Int )

    suspend fun getString( key: String ) : String?

    suspend fun getBoolean( key: String ) : Boolean

    suspend fun getInt( key: String ) : Int?

    suspend fun remove( key: String )


    suspend fun <T> save(obj: T)

    suspend fun <T> delete( obj: T)

    fun observePapers(): LiveData<List<Paper>>

    fun observeRates(): LiveData<List<Rate>>

    suspend fun getAllPapers(): List<Paper>

    suspend fun getAllRates(): List<Rate>

    suspend fun <T> deleteAll( type: Class<T> )

}
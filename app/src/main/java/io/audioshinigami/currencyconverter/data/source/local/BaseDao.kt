package io.audioshinigami.currencyconverter.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

/**
 * BaseDao for the app database
 */

@Dao
interface BaseDao<T> {

    @Insert
    fun add(obj: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll( data: List<T>)

    @Insert
    fun add(vararg objects: T)

    @Delete
    fun delete(obj: T)
}
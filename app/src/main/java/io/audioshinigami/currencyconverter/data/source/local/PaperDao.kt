package io.audioshinigami.currencyconverter.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import io.audioshinigami.currencyconverter.data.Paper
import io.audioshinigami.currencyconverter.utils.PAPER_TABLE_NAME

@Dao
interface PaperDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(obj: Paper)

    @Insert
    fun add( data: List<Paper>)

    @Insert
    fun add(vararg objects: Paper)

    @Delete
    fun delete(paper:Paper)

    @Query("SELECT * FROM $PAPER_TABLE_NAME ORDER BY paperId ASC")
    fun observeAllPaper(): LiveData<List<Paper>>

    @Query("SELECT * FROM $PAPER_TABLE_NAME ORDER BY paperId ASC")
    suspend fun getAllPaper(): List<Paper>

    @Query("DELETE FROM $PAPER_TABLE_NAME")
    suspend fun deleteAllPaper()
}
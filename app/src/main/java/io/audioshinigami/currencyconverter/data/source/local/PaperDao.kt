package io.audioshinigami.currencyconverter.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import io.audioshinigami.currencyconverter.data.Paper
import io.audioshinigami.currencyconverter.utils.PAPER_TABLE_NAME

@Dao
abstract class PaperDao: BaseDao<Paper> {

    @Query("SELECT * FROM $PAPER_TABLE_NAME ORDER BY rowid ASC")
    abstract fun observeAllPaper(): LiveData<List<Paper>>

    @Query("SELECT * FROM $PAPER_TABLE_NAME ORDER BY rowid ASC")
    abstract suspend fun getAllPaper(): List<Paper>

    @Query("DELETE FROM $PAPER_TABLE_NAME")
    abstract suspend fun deleteAllPaper()
}
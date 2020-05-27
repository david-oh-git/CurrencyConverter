package io.audioshinigami.currencyconverter.data

import androidx.lifecycle.LiveData

interface PaperDatabaseSource {

    suspend fun save(paper: Paper)

    suspend fun save(papers: List<Paper>)

    fun observePapers(): LiveData<List<Paper>>

    suspend fun getAllPapers(): List<Paper>

    suspend fun deleteAllPaper()
}
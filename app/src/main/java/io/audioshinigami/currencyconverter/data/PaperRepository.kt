package io.audioshinigami.currencyconverter.data

import androidx.lifecycle.LiveData

interface PaperRepository {

    suspend fun getPapers(): List<Paper>

    fun observePapers(): LiveData<List<Paper>>

    suspend fun save(paper: Paper)

    suspend fun save(papers: List<Paper>)

    suspend fun deleteAll()
}
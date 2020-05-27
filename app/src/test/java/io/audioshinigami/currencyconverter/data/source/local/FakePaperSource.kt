package io.audioshinigami.currencyconverter.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.audioshinigami.currencyconverter.data.Paper
import io.audioshinigami.currencyconverter.data.PaperDatabaseSource

class FakePaperSource(
    var paperStorage: MutableList<Paper> = mutableListOf()
): PaperDatabaseSource {
    override suspend fun save(paper: Paper) {
        paperStorage.add(paper)
    }

    override suspend fun save(papers: List<Paper>) {
        paperStorage.addAll(papers)
    }

    override fun observePapers(): LiveData<List<Paper>> {
        return MutableLiveData(paperStorage)
    }

    override suspend fun getAllPapers(): List<Paper> = paperStorage

    override suspend fun deleteAllPaper() {
        paperStorage.clear()
    }
}
package io.audioshinigami.currencyconverter.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.runBlocking

/**
 * Implementation of [PaperRepository] for easy testing
 */

class FakePaperRepoImpl: PaperRepository {

    var paperDb: LinkedHashMap<Int, Paper> = LinkedHashMap()
    private val observablePapers = MutableLiveData<List<Paper>>()

    private suspend fun reloadPaperData(){
        observablePapers.value = getPapers()
    }

    override suspend fun getPapers(): List<Paper> {
        return paperDb.values.toList()
    }

    override fun observePapers(): LiveData<List<Paper>> {
        runBlocking { reloadPaperData() }

        return observablePapers
    }

    override suspend fun save(paper: Paper) {
        paperDb[paper.paperId] = paper
    }

    override suspend fun save(papers: List<Paper>) {
        for( paper in papers){
            paperDb[paper.paperId] = paper
        }

        reloadPaperData()
    }

    override suspend fun deleteAll() {
        paperDb.clear()
        reloadPaperData()
    }
}
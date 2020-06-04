package io.audioshinigami.currencyconverter.data.source.local

import io.audioshinigami.currencyconverter.data.Paper
import io.audioshinigami.currencyconverter.data.PaperDatabaseSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of PaperDatabaseSource, mainly to provide a list of all currencies from the DB.
 */

class PaperSource @Inject constructor(
    private val paperDao: PaperDao,
    private val ioDispatcher: CoroutineDispatcher
): PaperDatabaseSource {
    override suspend fun save(paper: Paper) = withContext(ioDispatcher){
        paperDao.add(paper)
    }

    override suspend fun save(papers: List<Paper>) = withContext(ioDispatcher){
        paperDao.add(papers)
    }

    override fun observePapers() = paperDao.observeAllPaper()

    override suspend fun getAllPapers(): List<Paper> = withContext(ioDispatcher){
        return@withContext paperDao.getAllPaper()
    }

    override suspend fun deleteAllPaper() = withContext(ioDispatcher){
        paperDao.deleteAllPaper()
    }
}
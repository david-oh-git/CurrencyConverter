package io.audioshinigami.currencyconverter.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PaperRepoImpl @Inject constructor(
    private val paperDatabaseSource: PaperDatabaseSource,
    private val ioDispatcher: CoroutineDispatcher
): PaperRepository {
    override suspend fun getPapers(): List<Paper> = withContext(ioDispatcher){
        paperDatabaseSource.getAllPapers()
    }

    override fun observePapers(): LiveData<List<Paper>> {
        return paperDatabaseSource.observePapers()
    }

    override suspend fun save(paper: Paper) = withContext(ioDispatcher){
        paperDatabaseSource.save(paper)
    }

    override suspend fun save(papers: List<Paper>) = withContext(ioDispatcher){
        paperDatabaseSource.save(papers)
    }

    override suspend fun deleteAll() = withContext(ioDispatcher){
        paperDatabaseSource.deleteAllPaper()
    }
}
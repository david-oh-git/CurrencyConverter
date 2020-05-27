package io.audioshinigami.currencyconverter.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PaperRepoImpl(
    val paperDatabaseSource: PaperDatabaseSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): PaperRepository {
    override suspend fun getPapers(): List<Paper> = withContext(ioDispatcher){
        TODO("Not yet implemented")
    }

    override fun observePapers(): LiveData<List<Paper>> {
        TODO("Not yet implemented")
    }

    override suspend fun save(paper: Paper) {
        TODO("Not yet implemented")
    }

    override suspend fun save(papers: List<Paper>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }
}
package io.audioshinigami.currencyconverter.data

import com.google.common.truth.Truth.assertThat
import io.audioshinigami.currencyconverter.data.source.local.FakePaperSource
import io.audioshinigami.currencyconverter.utils.PaperFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class PaperRepoImplTest {

    private val paperDb: MutableList<Paper> = PaperFactory.getPaperEntries().toMutableList()

    private lateinit var paperRepository: PaperRepository
    private lateinit var paperDatabaseSource: PaperDatabaseSource

    @Before
    fun init(){
        paperDatabaseSource = FakePaperSource(paperDb)
        paperRepository = PaperRepoImpl(
            paperDatabaseSource,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun deleteAllPaper_DeleteAllPaperFromLocalDataSource() = runBlockingTest {
        // Arrange : create multiple paper variables & save
        paperRepository.save( PaperFactory.getPaperEntries() )

        // Act: delete all
        paperRepository.deleteAll()

        // Assert :
        val result = paperRepository.getPapers()
        assertThat(result.isEmpty())
    }

    @Test
    fun savePaper_confirmPaperIsSaved() = runBlockingTest {
        // Arrange : create Paper Variable
        val savePaper = PaperFactory.getPaper()

        // Act: save rate
        paperRepository.save(savePaper)

        // Assert
        val result = paperRepository.getPapers()
        assertThat(result).contains(savePaper)
    }

}
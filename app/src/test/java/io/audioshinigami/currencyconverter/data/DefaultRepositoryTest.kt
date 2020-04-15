package io.audioshinigami.currencyconverter.data

import com.google.common.truth.Truth.assertThat
import io.audioshinigami.currencyconverter.data.source.local.FakeDatabaseSource
import io.audioshinigami.currencyconverter.data.source.local.FakePreferenceSource
import io.audioshinigami.currencyconverter.data.source.remote.FakeRemoteDataSource
import io.audioshinigami.currencyconverter.network.Result
import io.audioshinigami.currencyconverter.utils.DEFAULT_PREF_INT_VALUE
import io.audioshinigami.currencyconverter.utils.PaperFactory
import io.audioshinigami.currencyconverter.utils.RateFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for implementation of repository
 */

@ExperimentalCoroutinesApi
class DefaultRepositoryTest {

    private val paperDb: MutableList<Paper> = PaperFactory.getPaperEntries().toMutableList()
    private val rateDb: MutableList<Rate> = RateFactory.rates.toMutableList()
    private val sharedPreferences: MutableMap<String, Any> = mutableMapOf()

    private lateinit var databaseSource: DatabaseSource
    private lateinit var sharedPreferenceSource: SharedPreferenceSource
    private lateinit var repository: AppRepository
    private lateinit var remoteDataSource: DatabaseSource

    @Before
    fun init(){

        databaseSource = FakeDatabaseSource(paperDb,rateDb)
        sharedPreferenceSource = FakePreferenceSource(sharedPreferences)
        remoteDataSource = FakeRemoteDataSource( mutableListOf() )

        repository = DefaultRepository(
            databaseSource,
            sharedPreferenceSource,
            remoteDataSource,
            Dispatchers.Unconfined
        )

    }

    @Test
    fun getAllRate_AllRatesFromRemoteDataSource() = runBlockingTest {
        // Arrange: create currency code
        val code = "NGN_EUR" // naira to pounds

        //Act: request for rate value from remote source
        val result = repository.getResult(code) as Result.Success

        // Assert: confirm result values
        assertThat( result.data.isNotEmpty() , `is`(true) )

    }

    @Test
    fun saveInteger_getFromSharedPreferenceSource() = runBlockingTest {
        // Arrange : save integer value
        val VALUE_KEY = "value_key"
        val value = 789
        repository.save(VALUE_KEY, value)

        // Act: retrieve the value
        val result = repository.getInt(VALUE_KEY)

        // Assert: confirm result is same as value
        assertThat(value, `is`(result))
    }

    @Test
    fun saveString_getFromSharedPreferenceSource() = runBlockingTest {
        // Arrange : save integer value
        val VALUE_KEY = "value_key"
        val value = "my cat"
        repository.save(VALUE_KEY, value)

        // Act: retrieve the value
        val result = repository.getString(VALUE_KEY)

        // Assert: confirm result is same as value
        assertThat(value, `is`(result))
    }

    @Test
    fun saveBoolean_getFromSharedPreferenceSource() = runBlockingTest {
        // Arrange : save integer value
        val VALUE_KEY = "value_key"
        val value = true
        repository.save(VALUE_KEY, value)

        // Act: retrieve the value
        val result = repository.getBoolean(VALUE_KEY)

        // Assert: confirm result is same as value
        assertThat(value, `is`(result) )
    }

    @Test
    fun removeKey_confirmFromSharedPreferenceSource() = runBlockingTest {
        // Arrange : save integer value
        val VALUE_KEY = "value_key"
        val VALUE_KEY2 = "value_key2"
        val value = 789
        val value2 = 300
        repository.save(VALUE_KEY, value)
        repository.save(VALUE_KEY2, value2)

        // Act: retrieve the value
        repository.remove(VALUE_KEY)
        val resultInt = repository.getInt(VALUE_KEY)
        val resultBoolean = repository.getBoolean(VALUE_KEY)
        val resultString = repository.getString(VALUE_KEY)

        // Assert: confirm result is same as value
        assertThat(resultInt, `is`(DEFAULT_PREF_INT_VALUE))
        assertThat(resultString, `is`(""))
        assertThat(resultBoolean, `is`(false))

    }

    @Test
    fun deleteAllRates_DeleteAllRatesFromLocalDataSource() = runBlockingTest {
        // Arrange: create multiple rate variables & save
        RateFactory.rates.forEach {
            repository.save(it)
        }

        // Act : delete all rates
        repository.deleteAll(Rate::class.java)

        // Assert: confirm empty rate DB
        val result = repository.getAllRates()
        assertThat( result.isEmpty() )
    }

    @Test
    fun deleteAllPaper_DeleteAllPaperFromLocalDataSource() = runBlockingTest {
        // Arrange : create multiple paper variables & save
        PaperFactory.getPaperEntries().map {
            repository.save(it)
        }

        // Act: delete all
        repository.deleteAll(Paper::class.java)

        // Assert :
        val result = repository.getAllPapers()
        assertThat(result.isEmpty())
    }

    @Test
    fun saveRate_confirmRateIsSaved() = runBlockingTest {
        // Arrange : create Rate Variable
        val saveRate = RateFactory.rate

        // Act: save rate
        repository.save(saveRate)

        // Assert
        val result = repository.getAllRates()
        assertThat(result).contains(saveRate)
    }

    @Test
    fun savePaper_confirmPaperIsSaved() = runBlockingTest {
        // Arrange : create Paper Variable
        val savePaper = PaperFactory.getPaper()

        // Act: save rate
        repository.save(savePaper)

        // Assert
        val result = repository.getAllPapers()
        assertThat(result).contains(savePaper)
    }

    @Test
    fun deleteRate_confirmRateIsDeleted() = runBlockingTest {
        // Arrange : create Rate Variable & save it
        val saveRate = RateFactory.rate
        repository.save(saveRate)
        repository.save( RateFactory.rate)

        // Act: delete rate
        repository.delete(saveRate)

        // Assert
        val result = repository.getAllRates()
        assertThat(result).doesNotContain(saveRate)
    }

    @Test
    fun deletePaper_confirmPaperIsDeleted() = runBlockingTest {
        // Arrange : create Rate Variable & save it
        val paper = PaperFactory.getPaper()
        repository.save(paper)

        // Act: delete rate
        repository.delete(paper)

        // Assert
        val result = repository.getAllPapers()
        assertThat(result).doesNotContain(paper)
    }
}
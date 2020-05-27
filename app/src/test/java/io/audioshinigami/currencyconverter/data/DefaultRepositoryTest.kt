package io.audioshinigami.currencyconverter.data

import com.google.common.truth.Truth.assertThat
import io.audioshinigami.currencyconverter.data.source.local.FakeDatabaseSource
import io.audioshinigami.currencyconverter.data.source.local.FakePreferenceSource
import io.audioshinigami.currencyconverter.data.source.remote.FakeRemoteDataSource
import io.audioshinigami.currencyconverter.network.Result
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

    private val rateDb: MutableList<Rate> = RateFactory.rates.toMutableList()
    private val sharedPreferences: MutableMap<String, Any> = mutableMapOf()

    private lateinit var databaseSource: DatabaseSource
    private lateinit var sharedPreferenceSource: SharedPreferenceSource
    private lateinit var repository: AppRepository
    private lateinit var remoteDataSource: DatabaseSource

    @Before
    fun init(){

        databaseSource = FakeDatabaseSource(rateDb)
        sharedPreferenceSource = FakePreferenceSource(sharedPreferences)
        remoteDataSource = FakeRemoteDataSource( mutableListOf() )

        repository = DefaultRepository(
            databaseSource,
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

}
/*
 * MIT License
 *
 * Copyright (c) 2020  David Osemwota
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.audioshinigami.currencyconverter.data

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import io.audioshinigami.currencyconverter.di.components.DaggerTestAppComponent
import io.audioshinigami.currencyconverter.di.components.TestAppComponent
import io.audioshinigami.currencyconverter.utils.RateFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

/**
 * Unit tests for implementation of repository
 */

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class DefaultRepositoryTest {

    @Inject lateinit var repository: DefaultRepository

    @Before
    fun init(){
        // init dagger2
        val applicationContext = ApplicationProvider.getApplicationContext<Context>()
        val component: TestAppComponent = DaggerTestAppComponent.factory().create(applicationContext)

        component.inject(this)
    }

    @Test
    fun getAllRate_AllRatesFromRemoteDataSource() = runBlockingTest {
        // Arrange: create currency code
        val code = "NGN_EUR" // naira to pounds

        //Act: request for rate value from remote source
        val result = repository.getResult(code)

        // Assert: confirm result values TODO write this test sir Davidoff

    }


    @Test
    fun deleteAllRates_DeleteAllRatesFromLocalDataSource() = runBlockingTest {
        // Arrange: create multiple rate variables & save
        RateFactory.rates.forEach {
            repository.save(it)
        }

        // Act : delete all rates
        repository.deleteAll()

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
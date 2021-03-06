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

package io.audioshinigami.currencyconverter.data.source.local

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import io.audioshinigami.currencyconverter.di.components.DaggerTestAppComponent
import io.audioshinigami.currencyconverter.di.components.TestAppComponent
import io.audioshinigami.currencyconverter.utils.PaperFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class ExchangeDatabaseTest {

    private lateinit var paperDao: PaperDao
    private lateinit var rateDao: RateDao
    @Inject lateinit var db: ExchangeDatabase

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init(){
        val applicationContext = ApplicationProvider.getApplicationContext<Context>()

        val component: TestAppComponent = DaggerTestAppComponent.factory().create(applicationContext)

        component.inject(this)

        paperDao = db.paperDao()
        rateDao = db.rateDao()
    }

    @Test
    fun addPaperToDb_confirmAdded() = runBlockingTest {
        // Arrange : create Paper class instance
        val paper1 = PaperFactory.getPaper()
        val paper2 = PaperFactory.getPaper()
        paperDao.add(paper1,paper2)

        // Act: get all paper from DB
        val result = paperDao.getAllPaper()

        // Assert
        assertThat(result.size, `is`(2))
    }

    @Test
    fun addListOfPaper_confirmAdded() = runBlockingTest {
        // Arrange : add list of [Paper]  to db
        paperDao.add( listOf(PaperFactory.getPaper(), PaperFactory.getPaper(), PaperFactory.getPaper() ) )

        // Act: get all paper from db
        val result = paperDao.getAllPaper()

        // Assert: number of [Paper] in db
        assertThat(result.size, `is`(3) )
    }

    @After
    @Throws(Exception::class)
    fun closeDb(){
        db.close()
    }
}
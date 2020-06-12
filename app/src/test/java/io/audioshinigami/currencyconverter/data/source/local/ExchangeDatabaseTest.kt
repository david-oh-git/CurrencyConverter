/*
 * Copyright 2020 Audio Shinigami Studio.
 *  All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.audioshinigami.currencyconverter.data.source.local

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
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

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(maxSdk = Build.VERSION_CODES.P)
class ExchangeDatabaseTest {

    private lateinit var paperDao: PaperDao
    private lateinit var rateDao: RateDao
    private lateinit var db: ExchangeDatabase

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ExchangeDatabase::class.java
        ).allowMainThreadQueries().build()

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

    @After
    @Throws(Exception::class)
    fun closeDb(){
        db.close()
    }
}
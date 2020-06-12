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
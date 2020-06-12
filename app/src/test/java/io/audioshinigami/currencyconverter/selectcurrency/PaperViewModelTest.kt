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

package io.audioshinigami.currencyconverter.selectcurrency

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.audioshinigami.currencyconverter.data.FakePaperRepoImpl
import io.audioshinigami.currencyconverter.utils.PaperFactory
import io.audioshinigami.currencyconverter.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class PaperViewModelTest {

    // class to be tested
    private lateinit var paperViewModel: PaperViewModel

    // fake repository for the test
    private lateinit var paperRepository: FakePaperRepoImpl

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() = runBlockingTest{

        paperRepository = FakePaperRepoImpl()

        paperRepository.save( PaperFactory.getPaperEntries())

        paperViewModel = PaperViewModel(paperRepository)
    }

    @Test
    fun searchPapers_getMatches(){
        // create and set search query
        val SEARCH_QUERY = "US"
        paperViewModel.setQuery(SEARCH_QUERY)

        // get all papers
        val result = paperViewModel.papers.getOrAwaitValue()

        // confirm
        MatcherAssert.assertThat(result.isNotEmpty(), `is`(true))
        MatcherAssert.assertThat(result.size, `is`(1))
    }
}
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

package io.audioshinigami.currencyconverter.selectcurrency

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.audioshinigami.currencyconverter.data.FakePaperRepoImpl
import io.audioshinigami.currencyconverter.data.PaperRepository
import io.audioshinigami.currencyconverter.utils.PaperFactory
import io.audioshinigami.currencyconverter.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class PaperViewModelTest {

    // class to be tested
    private lateinit var paperViewModel: PaperViewModel

    // fake repository for the test
    private lateinit var paperRepository: PaperRepository

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
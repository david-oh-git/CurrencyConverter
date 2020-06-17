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

import androidx.lifecycle.*
import io.audioshinigami.currencyconverter.data.AppRepository
import io.audioshinigami.currencyconverter.data.Paper
import io.audioshinigami.currencyconverter.data.PaperRepository
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class PaperViewModel @Inject constructor(
    private val repository: PaperRepository,
    private val appRepository: AppRepository
): ViewModel() {

    val searchQuery = MutableLiveData<String>()
    val papers: LiveData<List<Paper>> = Transformations.switchMap(searchQuery) { searchQuery ->

        Transformations.switchMap( repository.observePapers()) {papers ->
            searchPapers(searchQuery, papers)
        }
    }

    val items = repository.observePapers()

    // for testing
    fun setQuery(query: String){
        searchQuery.value = query
    }

    fun setFromCode(code: String) = viewModelScope.launch {
        appRepository.setFromCode(code)
    }

    fun setToCode(code: String) = viewModelScope.launch {
        appRepository.setToCode(code)
    }

    private fun searchPapers(query: String, papers: List<Paper>): LiveData<List<Paper>> {
        val result = MutableLiveData<List<Paper>>()

        if(query.isEmpty() || query.isBlank()){
            result.value = papers
            return result
        }

        val queryLowerCase = query.toLowerCase(Locale.ROOT)

        val searchPapers = ArrayList<Paper>()
        for (paper in papers){
            if( paper.name.toLowerCase(Locale.ROOT).contains(queryLowerCase)
                || paper.code.toLowerCase(Locale.ROOT).contains(queryLowerCase)){
                searchPapers.add(paper)
            }
        }

        result.value = searchPapers

        return result
    }
}
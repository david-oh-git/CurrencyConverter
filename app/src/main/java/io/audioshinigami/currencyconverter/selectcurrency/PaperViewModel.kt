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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import io.audioshinigami.currencyconverter.data.Paper
import io.audioshinigami.currencyconverter.data.PaperRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Provides a list of all currencies represented as [Paper] , include search functionality
 * with Transformations
 */
class PaperViewModel @Inject constructor(
    private val repository: PaperRepository
): ViewModel() {

    val searchQuery = MutableLiveData<String>().apply { value = "" }

    val papers: LiveData<List<Paper>> = Transformations.switchMap(searchQuery) { query ->

        Transformations.switchMap( repository.getPapers().asLiveData()) { papers ->
            papers?.let {
                searchPapers(query, papers)
            }
        }
    }

    // for testing
    fun setQuery(query: String){
        searchQuery.value = query
    }

    fun setFromCode(code: String) = viewModelScope.launch {
        repository.setFromCode(code)
    }

    fun setToCode(code: String) = viewModelScope.launch {
        repository.setToCode(code)
    }

    private fun searchPapers(query: String, papers: List<Paper>): LiveData<List<Paper>> {

        if( query.isEmpty() || query.isBlank()){
            return MutableLiveData(papers)
        }

        val result = MutableLiveData<List<Paper>>()

        val searchPapers = ArrayList<Paper>()
        for (paper in papers){
            if( paper.name.contains(query, ignoreCase = true)
                || paper.code.contains(query, ignoreCase = true) || paper.countryName.contains(query, ignoreCase = true) ){
                searchPapers.add(paper)
            }
        }

        result.value = searchPapers

        return result
    }
}
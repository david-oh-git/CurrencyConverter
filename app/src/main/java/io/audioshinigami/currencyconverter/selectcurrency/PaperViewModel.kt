package io.audioshinigami.currencyconverter.selectcurrency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import io.audioshinigami.currencyconverter.data.Paper
import io.audioshinigami.currencyconverter.data.PaperRepository


class PaperViewModel(
    private val repository: PaperRepository
): ViewModel() {

    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> = _searchQuery
    val papers: LiveData<List<Paper>> = Transformations.switchMap(searchQuery) { searchQuery ->

        Transformations.switchMap( repository.observePapers()) {papers ->
            searchPapers(searchQuery, papers)
        }
    }

    // for testing
    fun setQuery(query: String){
        _searchQuery.value = query
    }

    private fun searchPapers(query: String, papers: List<Paper>): LiveData<List<Paper>> {
        val result = MutableLiveData<List<Paper>>()

        if(query.isEmpty() || query.isBlank()){
            result.value = papers
            return result
        }

        val searchPapers = ArrayList<Paper>()
        for (paper in papers){
            if( paper.name.contains(query)){
                searchPapers.add(paper)
            }
        }

        result.value = searchPapers

        return result
    }
}
package io.audioshinigami.currencyconverter.selectcurrency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import io.audioshinigami.currencyconverter.data.Paper
import io.audioshinigami.currencyconverter.data.PaperRepository
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class PaperViewModel @Inject constructor(
    private val repository: PaperRepository
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
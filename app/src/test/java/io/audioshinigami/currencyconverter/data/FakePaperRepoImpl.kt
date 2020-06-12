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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.runBlocking

/**
 * Implementation of [PaperRepository] for easy testing
 */

class FakePaperRepoImpl: PaperRepository {

    var paperDb: LinkedHashMap<Int, Paper> = LinkedHashMap()
    private val observablePapers = MutableLiveData<List<Paper>>()

    private suspend fun reloadPaperData(){
        observablePapers.value = getPapers()
    }

    override suspend fun getPapers(): List<Paper> {
        return paperDb.values.toList()
    }

    override fun observePapers(): LiveData<List<Paper>> {
        runBlocking { reloadPaperData() }

        return observablePapers
    }

    override suspend fun save(paper: Paper) {
        paperDb[paper.paperId] = paper
    }

    override suspend fun save(papers: List<Paper>) {
        for( paper in papers){
            paperDb[paper.paperId] = paper
        }

        reloadPaperData()
    }

    override suspend fun deleteAll() {
        paperDb.clear()
        reloadPaperData()
    }
}
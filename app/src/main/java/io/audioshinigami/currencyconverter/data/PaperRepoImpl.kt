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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PaperRepoImpl @Inject constructor(
    private val paperDatabaseSource: PaperDatabaseSource,
    private val ioDispatcher: CoroutineDispatcher
): PaperRepository {
    override suspend fun getPapers(): List<Paper> = withContext(ioDispatcher){
        paperDatabaseSource.getAllPapers()
    }

    override fun observePapers(): LiveData<List<Paper>> {
        return paperDatabaseSource.observePapers()
    }

    override suspend fun save(paper: Paper) = withContext(ioDispatcher){
        paperDatabaseSource.save(paper)
    }

    override suspend fun save(papers: List<Paper>) = withContext(ioDispatcher){
        paperDatabaseSource.save(papers)
    }

    override suspend fun deleteAll() = withContext(ioDispatcher){
        paperDatabaseSource.deleteAllPaper()
    }
}
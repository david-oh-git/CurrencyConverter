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

import io.audioshinigami.currencyconverter.data.Paper
import io.audioshinigami.currencyconverter.data.PaperDatabaseSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of PaperDatabaseSource, mainly to provide a list of all currencies from the DB.
 */

class PaperSource @Inject constructor(
    private val paperDao: PaperDao,
    private val ioDispatcher: CoroutineDispatcher
): PaperDatabaseSource {
    override suspend fun save(paper: Paper) = withContext(ioDispatcher){
        paperDao.add(paper)
    }

    override suspend fun save(papers: List<Paper>) = withContext(ioDispatcher){
        paperDao.add(papers)
    }

    override fun observePapers() = paperDao.observeAllPaper()

    override suspend fun getAllPapers(): List<Paper> = withContext(ioDispatcher){
        return@withContext paperDao.getAllPaper()
    }

    override suspend fun deleteAllPaper() = withContext(ioDispatcher){
        paperDao.deleteAllPaper()
    }
}
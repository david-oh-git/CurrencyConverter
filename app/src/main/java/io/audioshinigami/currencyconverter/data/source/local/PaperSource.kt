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
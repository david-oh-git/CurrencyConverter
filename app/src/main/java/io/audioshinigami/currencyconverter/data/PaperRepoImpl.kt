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

package io.audioshinigami.currencyconverter.data

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import io.audioshinigami.currencyconverter.utils.FROM_CODE_KEY
import io.audioshinigami.currencyconverter.utils.TO_CODE_KEY
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PaperRepoImpl @Inject constructor(
    private val paperDatabaseSource: PaperDatabaseSource,
    private val sharedPreferences: SharedPreferences,
    private val ioDispatcher: CoroutineDispatcher
): PaperRepository {

    override suspend fun setFromCode(code: String) = withContext(ioDispatcher){
        sharedPreferences.edit {
            putString(FROM_CODE_KEY, code)
        }
    }

    override suspend fun setToCode(code: String) = withContext(ioDispatcher) {
        sharedPreferences.edit {
            putString(TO_CODE_KEY, code)
        }
    }

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
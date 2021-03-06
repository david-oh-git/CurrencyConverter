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

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.audioshinigami.currencyconverter.data.Paper
import io.audioshinigami.currencyconverter.utils.PAPER_TABLE_NAME

@Dao
interface PaperDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(obj: Paper)

    @Insert
    fun add( data: List<Paper>)

    @Insert
    fun add(vararg objects: Paper)

    @Delete
    fun delete(paper:Paper)

    @Query("SELECT * FROM $PAPER_TABLE_NAME ORDER BY paperId ASC")
    suspend fun getAllPaper(): List<Paper>

    @Query("DELETE FROM $PAPER_TABLE_NAME")
    suspend fun deleteAllPaper()
}
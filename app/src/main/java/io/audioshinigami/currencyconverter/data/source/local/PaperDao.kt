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

import androidx.lifecycle.LiveData
import androidx.room.*
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
    fun observeAllPaper(): LiveData<List<Paper>>

    @Query("SELECT * FROM $PAPER_TABLE_NAME ORDER BY paperId ASC")
    suspend fun getAllPaper(): List<Paper>

    @Query("DELETE FROM $PAPER_TABLE_NAME")
    suspend fun deleteAllPaper()
}
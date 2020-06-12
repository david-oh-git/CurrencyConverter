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

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import io.audioshinigami.currencyconverter.utils.RATES_TABLE_NAME

/**
 * class representing each currency conversion code
 * and its rate
 *
 * @param id id of the individual paper class
 * @param code currency code eg NGN_USD , code for nigerian naira to US dollars
 * @param rate converison rate for code
 */

@Entity(tableName = RATES_TABLE_NAME, indices = [ Index( value = ["code"], unique = true)] )
data class Rate(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name="rowid") val id: Int,
    @ColumnInfo val code: String,
    @ColumnInfo val rate: Double,
    @ColumnInfo val date: String
)
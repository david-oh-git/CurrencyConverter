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
import io.audioshinigami.currencyconverter.utils.PAPER_TABLE_NAME

/**
 * Class represents a currency unit
 *
 * @param paperId id of the individual paper class
 * @param name currency nam e.g Nigerian Naira
 * @param code currency code used by the API eg NGN
 */

@Entity(tableName = PAPER_TABLE_NAME, indices = [ Index( value = ["paperId"], unique = true)])
data class Paper(
    @PrimaryKey @ColumnInfo val paperId: Int,
    @ColumnInfo val name: String,
    @ColumnInfo val code: String,
    @ColumnInfo(name = "country_name") val countryName: String,
    @ColumnInfo val symbol: String,
    @ColumnInfo val imageUrl: String?
)
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
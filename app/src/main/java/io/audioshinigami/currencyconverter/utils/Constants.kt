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

package io.audioshinigami.currencyconverter.utils

import io.audioshinigami.currencyconverter.BuildConfig

const val api_key = BuildConfig.API_KEY
const val BASE_URL = "https://free.currconv.com"
const val currency_codes_total = 166
const val FROM_CODE_KEY = "from_code_key"
const val TO_CODE_KEY = "to_code_key"
const val FRAGMENT_CODE = "unknow_code"
const val PAPER_TABLE_NAME = "currency_db"
const val RATES_TABLE_NAME = "rates_db"
const val THEME_PREF_KEY = "io.audioshinigami.currencyconverter.utils.theme_key"
const val SETTINGS_PREF_NAME = "io.audioshinigami.currencyconverter.utils.settings_name"
const val DEFAULT_PREF_INT_VALUE = -999
const val APP_DB_FILENAME = "io.audioshinigami.currencyconverter.utils.app_db.db"
const val DATASOURCE_LOCAL = "LocalDatabaseSource"
const val DATASOURCE_REMOTE = "RemoteDataSource"
const val PAPER_DATA_FILENAME = "currencies.json"
const val CURRENCY_CODE = "currency_code"
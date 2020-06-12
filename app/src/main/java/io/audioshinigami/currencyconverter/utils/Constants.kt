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
package io.audioshinigami.currencyconverter.utils

import io.audioshinigami.currencyconverter.BuildConfig

const val api_key = BuildConfig.API_KEY
const val BASE_URL = "https://free.currconv.com"
const val currency_codes_total = 166
const val FROM_CODE_KEY = "from_code_key"
const val TO_CODE_KEY = "to_code_key"
const val UNKNOWN_CODE = "unknow_code"
const val PAPER_TABLE_NAME = "currency_db"
const val RATES_TABLE_NAME = "rates_db"
const val THEME_PREF_KEY = "io.audioshinigami.currencyconverter.utils.theme_key"
const val SETTINGS_PREF_NAME = "io.audioshinigami.currencyconverter.utils.settings_name"
const val DEFAULT_PREF_INT_VALUE = -999
const val APP_DB_FILENAME = "io.audioshinigami.currencyconverter.utils.app_db.db"
const val DATASOURCE_LOCAL = "LocalDatabaseSource"
const val DATASOURCE_REMOTE = "RemoteDataSource"
const val PAPER_DATA_FILENAME = "currencies.json"
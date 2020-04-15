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
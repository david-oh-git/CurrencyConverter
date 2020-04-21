package io.audioshinigami.currencyconverter.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import io.audioshinigami.currencyconverter.utils.PAPER_TABLE_NAME

/**
 * Class represents a currency unit
 *
 * @param id id of the individual paper class
 * @param name currency nam e.g Nigerian Naira
 * @param code currency code used by the API eg NGN
 */

@Entity(tableName = PAPER_TABLE_NAME, indices = [ Index( value = ["code"], unique = true)])
data class Paper(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name="rowid") val id: Int,
    @ColumnInfo val name: String,
    @ColumnInfo val code: String )
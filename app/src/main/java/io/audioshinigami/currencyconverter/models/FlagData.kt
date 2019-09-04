package io.audioshinigami.currencyconverter.models

object FlagData {
    private var codes: ArrayList<String>? = null
    private var flags: ArrayList<Int>? = null



    fun getCodes(block: () -> ArrayList<String>) =
        codes ?: block()

    fun getFlags( block:() -> ArrayList<Int> ) =
        synchronized(this){
            flags ?: block()
        }

}
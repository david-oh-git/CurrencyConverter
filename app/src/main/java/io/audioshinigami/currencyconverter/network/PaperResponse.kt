package io.audioshinigami.currencyconverter.network

import com.google.gson.annotations.JsonAdapter
import io.audioshinigami.currencyconverter.data.Paper

@JsonAdapter(PaperDeserializer::class)
data class PaperResponse(val data: List<Paper>)
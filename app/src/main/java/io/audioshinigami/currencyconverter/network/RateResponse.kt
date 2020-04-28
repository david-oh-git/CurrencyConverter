package io.audioshinigami.currencyconverter.network

import com.google.gson.annotations.JsonAdapter
import io.audioshinigami.currencyconverter.data.Rate

@JsonAdapter(RateDeserializer::class)
data class RateResponse(val data: List<Rate>)
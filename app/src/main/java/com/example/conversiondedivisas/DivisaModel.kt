package com.example.conversiondedivisas

import androidx.lifecycle.ViewModel
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class DivisaModel: ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.freecurrencyapi.com/v1/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val servicio = retrofit.create(DivisaService::class.java)

    suspend fun getDivisas(apikey: String): DivisaData{
        return servicio.getDivisas(apikey)
    }

    suspend fun getConversion(
            apikey: String,
            base_currency: String,
            currencies: String): ConversionData {
        return servicio.getConversion(apikey, base_currency, currencies)
    }
}
package com.example.conversiondedivisas

import retrofit2.http.GET
import retrofit2.http.Query

interface DivisaService {
    @GET("currencies")
    suspend fun getDivisas(
        @Query("apikey") apikey: String
    ): DivisaData

    @GET("latest")
    suspend fun getConversion(
        @Query("apikey") apikey: String,
        @Query("base_currency") base_currency: String,
        @Query("currencies") currencies: String
    ): ConversionData
}

package com.example.conversiondedivisas

import kotlinx.serialization.Serializable

@Serializable
data class DivisaData (
    val data: Map<String, Divisa>
)

@Serializable
data class Divisa (
    val name: String,
    val code: String
)
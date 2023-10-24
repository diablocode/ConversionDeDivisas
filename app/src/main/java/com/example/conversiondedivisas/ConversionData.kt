package com.example.conversiondedivisas

import kotlinx.serialization.Serializable

@Serializable
data class ConversionData (
    val data: Map<String, String>
)
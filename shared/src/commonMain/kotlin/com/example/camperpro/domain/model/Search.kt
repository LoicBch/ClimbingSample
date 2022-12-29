package com.example.camperpro.domain.model

data class Search(
    val id: Long,
    val categoryKey: String,
    val searchLabel: String,
    val timeStamp: Long,
    val lat: Double? = null,
    val lon: Double? = null
)
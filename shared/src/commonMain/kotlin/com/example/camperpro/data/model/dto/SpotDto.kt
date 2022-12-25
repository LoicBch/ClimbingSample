package com.example.camperpro.data.model.dto

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class SpotDto(

    var id: String = "",
    var name: String = "",
    var distance: String = "",
    var brands: String = "",
    var services: String = "",

    @SerialName("geolocatible")
    var geolocatable: String = "",
    var latitude: String = "",
    var longitude: String = "",
    var address: String = "",

    @SerialName("postal_code")
    var postalCode: String = "",
    var city: String = "",
    var country: String = "",

    @SerialName("country_iso")
    var countryIso: String = "",
    var phone: String = "",
    var email: String = "",
    var website: String = "",
    var facebook: String = "",
    var youtube: String? = "",
    var instagram: String? = "",
    var twitter: String = "",
    var premium: String = "",

    @SerialName("tel")
    var phone2: String? = null,
    var photos: List<PhotoDto> = listOf()
)

@kotlinx.serialization.Serializable
data class PhotoDto(
    @SerialName("photo")
    var url: String = ""
)
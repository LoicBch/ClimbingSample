package com.horionDev.climbingapp.managers.location

// on utilise uniquement les coordonee in app
data class LocationData(
    val accuracy: Double,
    val altitude: Double,
    val altitudeAccuracy: Double,
    val heading: Double,
    val speed: Double,
    val coordinates: Coordinates,
)

data class Coordinates(
    val latitude: Double,
    val longitude: Double,
)
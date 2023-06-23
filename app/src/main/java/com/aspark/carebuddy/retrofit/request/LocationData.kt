package com.aspark.carebuddy.retrofit.request

data class LocationData (
    val latitude: Double,
    val longitude: Double,
    val email: String?,
    val pincode: String?
)
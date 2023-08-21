package com.aspark.carebuddy.retrofit.request

data class BookAppointmentRequest(
    val userEmail: String,
    val nurseId: Int,
)
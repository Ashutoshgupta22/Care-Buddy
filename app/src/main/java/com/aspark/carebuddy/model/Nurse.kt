package com.aspark.carebuddy.model

class Nurse {

    var id =  0
    var name: String = ""
    var age = 0
    var email: String = ""
    var pincode: String = ""
    var firebaseToken: String = ""
    var latitude = 0.0
    var longitude = 0.0
    var userRole : String? = null
    var biography: String = ""
    var specialitiesList = arrayListOf<String>()
    var rating: Double = 0.0
    var patientNo: Int = 0
    var experience: Int = 0
    var imageUrl: String = ""
}
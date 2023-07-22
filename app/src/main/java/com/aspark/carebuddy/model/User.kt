package com.aspark.carebuddy.model

class User private constructor() {

    var id = 0
    var firstName: String? = null
    var lastName: String = ""
    var age = 0
    var latitude = 0.0
    var longitude = 0.0
    var email: String? = null
    var password: String? = null
    var firebaseToken: String? = null
    var pincode: String = ""


    companion object {
         var currentUser = User()
    }

    override fun toString(): String {
        return "User(id=$id, firstName=$firstName, age=$age, latitude=$latitude, longitude=$longitude," +
                " email=$email, password=$password, " +
                "firebaseToken=$firebaseToken, pincode='$pincode')"
    }
}
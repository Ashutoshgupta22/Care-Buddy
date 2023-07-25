package com.aspark.carebuddy.model

class Nurse {

    var id =  0
    var firstName: String = ""
    var lastName: String = ""
    var age = 0
    var email: String = ""
    var pincode: String = ""
    var firebaseToken: String = ""
    var latitude = 0.0
    var longitude = 0.0
    var userRole : String? = null
    var biography: String = ""
    var specialities: ArrayList<String> = arrayListOf()
    var qualifications: String = ""
    var rating: Double = 0.0
    var patientNo: Int = 0
    var experience: Int = 0
    var imageUrl: String = ""

    override fun toString(): String {
        return "Nurse(id=$id, firstName='$firstName'," +
                " lastName='$lastName', age=$age, email='$email', " +
                "pincode='$pincode', firebaseToken='$firebaseToken', " +
                "latitude=$latitude, longitude=$longitude, " +
                "userRole=$userRole, biography='$biography', " +
                "specialities=$specialities, qualifications='$qualifications'," +
                " rating=$rating, patientNo=$patientNo, " +
                "experience=$experience, imageUrl='$imageUrl')"
    }


}
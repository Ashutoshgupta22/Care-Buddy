package com.aspark.carebuddy.model

class User private constructor() {

    var id = 0
    var name: String? = null
    var age = 0
    var latitude = 0.0
    var longitude = 0.0
    var email: String? = null
    var password: String? = null

    override fun toString(): String {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}'
    }

    companion object {
         var currentUser = User()
    }
}
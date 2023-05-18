package com.aspark.carebuddy.model

 data class Nurse private constructor(
     val id: Int, var name: String,
     var age: Int, var email: String,
     var password: String, var pincode: String,
     var latitude: Double, var longitude: Double,
     var userRole : String, var locked: Boolean,
     var enabled: Boolean) {


     companion object{

            var currentNurse = Nurse(-1,"",-1,"",
                                    "","",-1.00,-1.00,
                                    "NURSE",false,false)
     }

 }
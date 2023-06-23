package com.aspark.carebuddy.retrofit

enum class HttpStatusCode(val code: Int) {

   OK(200),
   FAILED(400),
   UNAUTHORIZED(401),
   FORBIDDEN(403)
}
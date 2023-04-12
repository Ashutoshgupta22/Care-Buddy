package com.aspark.carebuddy

import com.aspark.carebuddy.retrofit.RetrofitService

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RetrofitServiceTest {

    @Test
    fun getRetrofitTest() {

        val service = RetrofitService()
        val retrofit = service.retrofit

        Assert.assertEquals("http://192.168.1.11:8080/",retrofit.baseUrl().toString())
    }

}
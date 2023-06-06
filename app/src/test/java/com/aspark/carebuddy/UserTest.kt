package com.aspark.carebuddy

import com.aspark.carebuddy.model.User
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserTest {

    @Test
    fun testCurrentUserInitialisation(){
        assertNotNull(User.currentUser)
    }

    @Test
    fun testCurrentUser(){

        val user1 = User.currentUser
        val user2 = User.currentUser

        assertEquals(user1,user2)
    }
}
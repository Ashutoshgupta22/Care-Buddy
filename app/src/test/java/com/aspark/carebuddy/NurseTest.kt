package com.aspark.carebuddy

import com.aspark.carebuddy.model.Nurse
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NurseTest {


    @Test
    fun testCurrentNurseInitialisation(){
        assertNotNull(Nurse.currentNurse)
    }

    @Test
    fun testCurrentNurse(){

        val nurse1 = Nurse.currentNurse
        val nurse2 = Nurse.currentNurse

        assertEquals(nurse1,nurse2)
    }
}
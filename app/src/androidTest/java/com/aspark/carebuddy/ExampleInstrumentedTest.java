package com.aspark.carebuddy;

import android.content.Context;
import android.content.Intent;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.aspark.carebuddy.presenter.SignUpPresenter;
import com.aspark.carebuddy.view.user.SignUpUserActivity;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
      //  assertEquals("com.aspark.carebuddy", appContext.getPackageName());


//        Contract.Presenter.PresenterSignUp presenter = new SignUpPresenter();
//        presenter.signUpClickListener(appContext.getApplicationContext());
    }
}
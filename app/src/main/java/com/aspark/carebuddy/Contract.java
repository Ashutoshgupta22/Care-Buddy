package com.aspark.carebuddy;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.aspark.carebuddy.model.UserModel;

public interface Contract {

     interface Model {

    }

    interface View {

         void destroyActivity();

         interface UserLoginView {
             void showUserLoginError(String Message);
             void hideUserLoginError();
         }

         interface MapView {

             void showMaps(Bundle savedInstanceState, Location location);
         }
    }

    interface Presenter {

        void nurseCardClickListener(Context context);
        void userCardClickListener(Context context);
        void signUpBtnClickListener(Context context);
        void loginNurseClickListener(Context context);

        interface PresenterSignUp  {

            void signUpClickListener(Context context,UserModel user);
        }
        interface PresenterUserLogin {

            void userLoginClickListener(Context context, String sEmail, String sPassword);
            void userSignedInLogin(Context context);
        }

        interface PresenterMap {

            void getLastLocation(Context context,Contract.View.MapView mapView,Bundle savedInstanceState);
            void confirmLocationClickListener(Context context,double latitude, double longitude);
        }

        interface PresenterUserHome{

            void bookService(Context context);

        }
    }

}

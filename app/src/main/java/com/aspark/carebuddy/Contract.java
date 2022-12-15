package com.aspark.carebuddy;

import android.content.Context;

import com.aspark.carebuddy.model.UserModel;

public interface Contract {

     interface Model {

    }

    interface View {

         interface UserLoginView {
             void showUserLoginError(String Message);
             void hideUserLoginError();
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
        }
    }

}

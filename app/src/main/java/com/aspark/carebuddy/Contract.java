package com.aspark.carebuddy;

import android.content.Context;

public interface Contract {

     interface Model {

    }

    interface View {

    }

    interface Presenter {

        void nurseCardClickListener(Context context);
        void userCardClickListener(Context context);
        void loginBtnUserClickListener(Context context);
        void signUpClickListener(Context context);
        void loginNurseClickListener(Context context);

        interface PresenterSignUp  {

            void signUpClickListener(Context context);


        }
    }

}

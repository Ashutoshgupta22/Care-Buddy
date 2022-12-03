package com.aspark.carebuddy.view.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.aspark.carebuddy.Contract;
import com.aspark.carebuddy.R;
import com.aspark.carebuddy.model.UserModel;
import com.aspark.carebuddy.presenter.SignUpPresenter;
import com.aspark.carebuddy.retrofit.RetrofitService;
import com.aspark.carebuddy.retrofit.UserApi;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpUserActivity extends AppCompatActivity {

    EditText nameEditText, ageEditText, emailEditText,passwordEditText, confirmPasswordEditText;
    Spinner genderSpinner;
    Button signUpBtn;
    String sName, sAge, sEmail,sPassword, sConfirmPassword;
    boolean validInput=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_user);

        nameEditText = findViewById(R.id.userSignUpName);
        ageEditText = findViewById(R.id.userSignUpAge);
        emailEditText = findViewById(R.id.userSignUpEmail);
        passwordEditText = findViewById(R.id.userSignUpPassword);
        confirmPasswordEditText = findViewById(R.id.userSignUpConfirmPassword);
        genderSpinner = findViewById(R.id.userSignUpGender);
        signUpBtn =findViewById(R.id.userSignUpBtn);

        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        signUpBtn.setOnClickListener(view -> {

            sName = nameEditText.getText().toString().trim();
            sAge = ageEditText.getText().toString().trim();
            sEmail = emailEditText.getText().toString().trim();
            sPassword = passwordEditText.getText().toString().trim();
            sConfirmPassword = confirmPasswordEditText.getText().toString().trim();

            validInput = true;
            if (verifyInput()) {

                UserModel user = new UserModel();
                user.setName(sName);
                user.setAge(Integer.parseInt(sAge));

                callSaveApi(user,userApi);

               Contract.Presenter.PresenterSignUp presenter = new SignUpPresenter();
               presenter.signUpClickListener(this);
           }
        });

    }

    private void callSaveApi(UserModel user, UserApi userApi) {

        userApi.saveUser(user)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        Toast.makeText(SignUpUserActivity.this, "Save successful!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        Toast.makeText(SignUpUserActivity.this, "Save Failed!!", Toast.LENGTH_LONG).show();
                        Logger.getLogger(SignUpUserActivity.class.getName()).log(Level.SEVERE,"Error Occurred",t);

                    }
                });

    }

    private boolean verifyInput() {

        if (sName.equals("")) {
            nameEditText.setError("Name can not be empty");
            validInput = false;
        }
        if (sAge.equals("")) {
            ageEditText.setError("Age can not be empty");
            validInput = false;
        }

        if (sEmail.equals("")) {
            emailEditText.setError("Email can not be empty");
            validInput = false;
        }
        if (sPassword.equals("")) {
            passwordEditText.setError("Password can not be empty");
            validInput = false;
        }
        else {

            if (sPassword.length() <6) {
                passwordEditText.setError("Password should contain atleast 6 characters ");
                validInput = false;
            }
                else {

                if (!sPassword.equals(sConfirmPassword)) {
                    confirmPasswordEditText.setError("Confirm Password did not match");
                    validInput = false;
                }
            }
        }

        return validInput;
    }
}
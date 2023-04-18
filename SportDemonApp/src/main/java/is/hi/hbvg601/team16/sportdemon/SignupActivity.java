package is.hi.hbvg601.team16.sportdemon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import is.hi.hbvg601.team16.sportdemon.services.UserService;
import is.hi.hbvg601.team16.sportdemon.services.implementations.NetworkManagerAPI;
import is.hi.hbvg601.team16.sportdemon.services.implementations.UserServiceImplementation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    public UserService mUserService;

    private ProgressBar progressBar;
    private LinearLayout form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        NetworkManagerAPI nmAPI = new NetworkManagerAPI();
        this.mUserService = new UserServiceImplementation(nmAPI);

        EditText emailEdit = findViewById(R.id.signup_email);
        EditText usernameEdit = findViewById(R.id.signup_username);
        EditText passwordEdit = findViewById(R.id.signup_password);
        EditText passwordConfEdit = findViewById(R.id.signup_passwordConf);

        progressBar = findViewById(R.id.signup_progress);
        form = findViewById(R.id.signup_form);

        Button submitBtn = findViewById(R.id.signup_submit_button);
        submitBtn.setOnClickListener(v -> {
            String email = emailEdit.getText().toString();
            String username = usernameEdit.getText().toString();
            String password = passwordEdit.getText().toString();
            String PasswordConf = passwordConfEdit.getText().toString();

            if (email.equals("") || username.equals("") ||
                password.equals("") || PasswordConf.equals("")) {
                Toast.makeText(this,
                        "Please fill all fields",
                        Toast.LENGTH_SHORT
                ).show();
            }else if (!password.equals(PasswordConf)) {
                Toast.makeText(this,
                        "Passwords Didn't Match",
                        Toast.LENGTH_SHORT
                ).show();
            } else {
                showLoading();

                User user = new User(username, password, email);

                Call<User> callSync = mUserService.createAccount(user);
                callSync.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call,
                                           @NonNull Response<User> response) {
                        if (response.isSuccessful()) {
                            // Get response
                            try {
                                if (response.body() != null) {
                                    // UI
                                    Toast.makeText(SignupActivity.super.getApplicationContext(),
                                            "Account Created",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                    hideLoading();

                                    // return
                                    Intent skil = new Intent();
                                    skil.putExtra("USER", response.body());
                                    setResult(RESULT_OK, skil);
                                    finish();
                                } else {
                                    // UI
                                    Toast.makeText(SignupActivity.super.getApplicationContext(),
                                            "Username taken",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                    hideLoading();
                                }
                            } catch (Exception e) {
                                // UI
                                Toast.makeText(SignupActivity.super.getApplicationContext(),
                                        e.toString(),
                                        Toast.LENGTH_SHORT
                                ).show();
                                hideLoading();
                            }
                        } else {
                            Toast.makeText(SignupActivity.super.getApplicationContext(),
                                    response.code()+" - "+response,
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                        Toast.makeText(SignupActivity.super.getApplicationContext(),
                                t.toString(),
                                Toast.LENGTH_SHORT
                        ).show();
                        hideLoading();
                    }
                });
            }
        });

        Button returnBtn = findViewById(R.id.signup_return_button);
        returnBtn.setOnClickListener(v -> finish());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserService = null;
    }

    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        form.setVisibility(View.INVISIBLE);
    }

    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
        form.setVisibility(View.VISIBLE);
    }
}

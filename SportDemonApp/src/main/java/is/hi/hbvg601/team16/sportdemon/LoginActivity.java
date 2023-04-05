package is.hi.hbvg601.team16.sportdemon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import is.hi.hbvg601.team16.sportdemon.services.UserService;
import is.hi.hbvg601.team16.sportdemon.services.implementations.NetworkManagerAPI;
import is.hi.hbvg601.team16.sportdemon.services.implementations.UserServiceImplementation;

public class LoginActivity extends AppCompatActivity {

    public UserService mUserService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        NetworkManagerAPI nmAPI = new NetworkManagerAPI();
        this.mUserService = new UserServiceImplementation(nmAPI);

        EditText usernameEdit = findViewById(R.id.login_username);
        EditText passwordEdit = findViewById(R.id.login_password);

        Button loginBtn = findViewById(R.id.login_button);
        loginBtn.setOnClickListener(v -> {
            String username = usernameEdit.getText().toString();
            String password = passwordEdit.getText().toString();
            User outcome = mUserService.login(username, password);

            if (outcome == null) {
                Toast.makeText(this,
                        "Username and/or password incorrect. Try again.",
                        Toast.LENGTH_LONG
                ).show();
            } else {
                Intent skil = new Intent();
                skil.putExtra("USER", outcome);
                setResult(-1, skil);

                finish();
            }
        });
    }
}
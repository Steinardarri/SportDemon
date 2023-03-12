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
        setContentView(R.layout.activity_signup);

        NetworkManagerAPI nmAPI = new NetworkManagerAPI();
        this.mUserService = new UserServiceImplementation(nmAPI);

        EditText emailEdit = findViewById(R.id.signup_email);
        EditText usernameEdit = findViewById(R.id.signup_username);
        EditText passwordEdit = findViewById(R.id.signup_password);
        EditText passwordConfEdit = findViewById(R.id.signup_passwordConf);

        Button submitBtn = findViewById(R.id.signup_submit_button);
        submitBtn.setOnClickListener( v -> {
            String email = emailEdit.getText().toString();
            String username = usernameEdit.getText().toString();
            String password = passwordEdit.getText().toString();
            String PasswordConf = passwordConfEdit.getText().toString();

            if (!password.equals(PasswordConf)) {
                Toast.makeText(this,
                        "Lykilorðin pössuðu ekki saman",
                        Toast.LENGTH_SHORT
                ).show();
            } else if (mUserService.findUserByUsername(username) != null) {
                Toast.makeText(this,
                        "Notendanafnið er ekki laust",
                        Toast.LENGTH_SHORT
                ).show();
            } else {
                User user = new User(username, password, email);
                String outcome = mUserService.createAccount(user);
                Toast.makeText(this,
                        outcome,
                        Toast.LENGTH_SHORT
                ).show();

                Intent skil = new Intent();
                if (outcome.equals("Success")) {
                    skil.putExtra("USER", user);
                    setResult(-1, skil);
                } else {
                    setResult(0, null);
                }
                finish();
            }
        });

        Button returnBtn = findViewById(R.id.signup_return_button);
        returnBtn.setOnClickListener( v -> finish());
    }
}


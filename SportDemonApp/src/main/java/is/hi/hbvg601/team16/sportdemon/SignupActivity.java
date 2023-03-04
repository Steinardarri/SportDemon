package is.hi.hbvg601.team16.sportdemon;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button returnBtn = findViewById(R.id.signup_return_button);
        returnBtn.setOnClickListener(
                (v) -> finish()
        );
    }
}

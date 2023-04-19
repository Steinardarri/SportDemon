package is.hi.hbvg601.team16.sportdemon;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;

public class PlayTrackerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_tracker);

        Workout workout = (Workout) getIntent().getSerializableExtra("WORKOUT");
        assert workout != null : "Workout is null";



    }

}

package is.hi.hbvg601.team16.sportdemon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;
import is.hi.hbvg601.team16.sportdemon.services.WorkoutService;
import is.hi.hbvg601.team16.sportdemon.services.implementations.NetworkManagerAPI;
import is.hi.hbvg601.team16.sportdemon.services.implementations.WorkoutServiceImplementation;

public class ExerciseComboActivity extends AppCompatActivity {

    public WorkoutService mWorkoutService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        NetworkManagerAPI nmAPI = new NetworkManagerAPI();
        this.mWorkoutService = new WorkoutServiceImplementation(nmAPI);

        ExerciseCombo ec = (ExerciseCombo) getIntent().getSerializableExtra("EXERCISECOMBO");

        EditText titleEdit = findViewById(R.id.exerciseName);
        EditText setsEdit = findViewById(R.id.exerciseSets);
        EditText repsEdit = findViewById(R.id.exerciseReps);
        EditText weightEdit = findViewById(R.id.exerciseWeight);
        EditText equipmentEdit = findViewById(R.id.exerciseEquipment);
        EditText durationPerSetEdit = findViewById(R.id.exerciseDuration);
        EditText restBetweenSetsEdit = findViewById(R.id.exerciseRest);

        Button submitBtn = findViewById(R.id.btnAddExercise);
        submitBtn.setOnClickListener( v -> {
            ec.setTitle(titleEdit.getText().toString());
            ec.setSets(Integer.parseInt(setsEdit.getText().toString()));
            ec.setReps(Integer.parseInt(repsEdit.getText().toString()));
            ec.setWeight(Double.parseDouble(weightEdit.getText().toString()));
            ec.setEquipment(equipmentEdit.getText().toString());
            ec.setDurationPerSet(Integer.parseInt(durationPerSetEdit.getText().toString()));
            ec.setRestBetweenSets(Integer.parseInt(restBetweenSetsEdit.getText().toString()));

            Intent skil = new Intent();
            skil.putExtra("EXERCISECOMBO", ec);
            setResult(RESULT_OK, skil);

            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWorkoutService = null;
    }
}

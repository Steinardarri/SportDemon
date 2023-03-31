package is.hi.hbvg601.team16.sportdemon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;
import is.hi.hbvg601.team16.sportdemon.services.WorkoutService;
import is.hi.hbvg601.team16.sportdemon.services.implementations.NetworkManagerAPI;
import is.hi.hbvg601.team16.sportdemon.services.implementations.WorkoutServiceImplementation;

public class ExerciseComboActivity extends AppCompatActivity {

    public WorkoutService mWorkoutService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_combo);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Workout workout = (Workout) bundle.getSerializable("WORKOUT");
        NetworkManagerAPI nmAPI = new NetworkManagerAPI();
        this.mWorkoutService = new WorkoutServiceImplementation(nmAPI);

        EditText titleEdit = findViewById(R.id.editTextName);
        EditText setsEdit = findViewById(R.id.editTextSets);
        EditText repsEdit = findViewById(R.id.editTextReps);
        EditText weightEdit = findViewById(R.id.editTextWeight);
        EditText equipmentEdit = findViewById(R.id.editTextEquipment);
        EditText durationPerSetEdit = findViewById(R.id.editTextDurationPerSet);
        EditText restBetweenSetsEdit = findViewById(R.id.editTextRestBetweenSets);

        Button submitBtn = findViewById(R.id.submit_button);
        submitBtn.setOnClickListener( v -> {
            String title = titleEdit.getText().toString();
            int sets = Integer.parseInt(setsEdit.getText().toString());
            int reps = Integer.parseInt(repsEdit.getText().toString());
            double weight = Double.parseDouble(weightEdit.getText().toString());
            String equipment = equipmentEdit.getText().toString();
            int durationPerSet = Integer.parseInt(durationPerSetEdit.getText().toString());
            int restBetweenSets = Integer.parseInt(restBetweenSetsEdit.getText().toString());

            ExerciseCombo exerciseCombo = new ExerciseCombo(title, sets, reps, weight, equipment, durationPerSet, restBetweenSets);

            mWorkoutService.addExerciseCombo(exerciseCombo, workout);

            setResult(RESULT_OK);
            finish();
        });
    }
}
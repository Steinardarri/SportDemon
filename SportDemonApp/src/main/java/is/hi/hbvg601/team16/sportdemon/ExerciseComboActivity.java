package is.hi.hbvg601.team16.sportdemon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dmax.dialog.SpotsDialog;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;
import is.hi.hbvg601.team16.sportdemon.services.WorkoutService;
import is.hi.hbvg601.team16.sportdemon.services.implementations.NetworkManagerAPI;
import is.hi.hbvg601.team16.sportdemon.services.implementations.WorkoutServiceImplementation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExerciseComboActivity extends AppCompatActivity {

    public WorkoutService mWorkoutService;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        NetworkManagerAPI nmAPI = new NetworkManagerAPI();
        this.mWorkoutService = new WorkoutServiceImplementation(nmAPI);


        ExerciseCombo ec = (ExerciseCombo) getIntent().getSerializableExtra("EXERCISECOMBO");
        assert ec != null : "ExerciseCombo is null";

        Button submitBtn = findViewById(R.id.btnAddExercise);
        EditText titleEdit = findViewById(R.id.exerciseName);
        EditText setsEdit = findViewById(R.id.exerciseSets);
        EditText repsEdit = findViewById(R.id.exerciseReps);
        EditText weightEdit = findViewById(R.id.exerciseWeight);
        EditText equipmentEdit = findViewById(R.id.exerciseEquipment);
        EditText durationPerSetEdit = findViewById(R.id.exerciseDuration);
        EditText restBetweenSetsEdit = findViewById(R.id.exerciseRest);

        // Ef ekki nýtt, þá sýna edit
        if (ec.getTitle() != null) {
            submitBtn.setText(R.string.edit_exercise);
            titleEdit.setText(ec.getTitle());
            setsEdit.setText(""+ec.getSets());
            repsEdit.setText(""+ec.getReps());
            weightEdit.setText(""+ec.getWeight());
            equipmentEdit.setText(ec.getEquipment());
            durationPerSetEdit.setText(""+ec.getDurationPerSet());
            restBetweenSetsEdit.setText(""+ec.getRestBetweenSets());
        }

        submitBtn.setOnClickListener( v -> {
            ec.setTitle(titleEdit.getText().toString());
            ec.setSets(Integer.parseInt(setsEdit.getText().toString()));
            ec.setReps(Integer.parseInt(repsEdit.getText().toString()));
            ec.setWeight(Double.parseDouble(weightEdit.getText().toString()));
            ec.setEquipment(equipmentEdit.getText().toString());
            ec.setDurationPerSet(Integer.parseInt(durationPerSetEdit.getText().toString()));
            ec.setRestBetweenSets(Integer.parseInt(restBetweenSetsEdit.getText().toString()));

            SpotsDialog loadingDialog = new SpotsDialog(this, "Setting up new Exercise");
            loadingDialog.show();
            // Setja upp nýtt ExerciseCombo á server
            Call<ExerciseCombo> callSync = mWorkoutService.saveExerciseCombo(ec);
            callSync.enqueue(new Callback<ExerciseCombo>() {
                @Override
                public void onResponse(@NonNull Call<ExerciseCombo> call, @NonNull Response<ExerciseCombo> response) {
                    if (response.isSuccessful()) {
                        // Get response
                        try {
                            Intent skil = new Intent();

                            skil.putExtra("EXERCISECOMBO", ec);
                            setResult(RESULT_OK, skil);

                            loadingDialog.dismiss();
                            finish();

                        } catch (Exception e) {
                            // UI
                            Toast.makeText(ExerciseComboActivity.this,
                                    e.toString(),
                                    Toast.LENGTH_SHORT
                            ).show();
                            loadingDialog.dismiss();
                        }
                    } else {
                        Toast.makeText(ExerciseComboActivity.this,
                                response.code()+" - "+response,
                                Toast.LENGTH_SHORT
                        ).show();
                        loadingDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ExerciseCombo> call, @NonNull Throwable t) {
                    Toast.makeText(ExerciseComboActivity.this,
                            t.toString(),
                            Toast.LENGTH_SHORT
                    ).show();
                    loadingDialog.dismiss();
                }
            });
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWorkoutService = null;
    }
}

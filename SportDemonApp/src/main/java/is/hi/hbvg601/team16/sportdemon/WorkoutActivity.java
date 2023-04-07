package is.hi.hbvg601.team16.sportdemon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;
import is.hi.hbvg601.team16.sportdemon.services.WorkoutService;
import is.hi.hbvg601.team16.sportdemon.services.implementations.NetworkManagerAPI;
import is.hi.hbvg601.team16.sportdemon.services.implementations.WorkoutServiceImplementation;
import is.hi.hbvg601.team16.sportdemon.ui.homeactivity.home.WorkoutsRecyclerViewAdapter;
import is.hi.hbvg601.team16.sportdemon.ui.workoutactivity.ExerciseComboRecyclerViewAdapter;

public class WorkoutActivity extends AppCompatActivity {

    public WorkoutService mWorkoutService;

    private Workout mWorkout;

    // Intent code
    private static final int RESULT_SUCCESS = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        mWorkout = (Workout) getIntent().getSerializableExtra("WORKOUT");

        NetworkManagerAPI nmAPI = new NetworkManagerAPI();
        this.mWorkoutService = new WorkoutServiceImplementation(nmAPI);

        User mUser = HomeActivity.getSportUser(); // Sækir aðal current user
        // frá Home activity

        // Setja upp RecyclerView fyrir ExerciseCombos
        RecyclerView mECRecyclerView = findViewById(R.id.workout_recyclerView);
        mECRecyclerView.setLayoutManager(new LinearLayoutManager(WorkoutActivity.this));


        ExerciseComboRecyclerViewAdapter adapter = new ExerciseComboRecyclerViewAdapter(
                WorkoutActivity.this, mWorkout.getExerciseCombo()
        );
        adapter.setClickListener(
                (View v, int position, List<ExerciseCombo> data) -> {
                    Intent i = new Intent(
                            WorkoutActivity.this, ExerciseComboActivity.class
                    );
                    i.putExtra("EXERCISECOMBO", data.get(position));
                    exerciseComboResultLauncher.launch(i);
                });
        mECRecyclerView.setAdapter(adapter);

        findViewById(R.id.workout_add_button).setOnClickListener(v -> {
            Intent i = new Intent(WorkoutActivity.this, ExerciseComboActivity.class);
            i.putExtra("EXERCISECOMBO", new ExerciseCombo());
            exerciseComboResultLauncher.launch(i);
        });

        findViewById(R.id.workout_edit_button).setOnClickListener(v -> {
            // TODO edit button
        });
    }

    private final ActivityResultLauncher<Intent> exerciseComboResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_SUCCESS) {
                    Intent data = result.getData();

                    refreshList();
                }
            }
    );

    private void refreshList() {
        RecyclerView mECRecyclerView = findViewById(R.id.workout_recyclerView);
        ExerciseComboRecyclerViewAdapter adapter =
                (ExerciseComboRecyclerViewAdapter) mECRecyclerView.getAdapter();
        assert adapter != null;
        adapter.setData(mWorkout.getExerciseCombo());
        mECRecyclerView.setAdapter(adapter);
    }
}
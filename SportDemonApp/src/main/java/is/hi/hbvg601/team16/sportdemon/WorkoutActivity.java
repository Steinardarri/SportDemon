package is.hi.hbvg601.team16.sportdemon;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.reactivex.rxjava3.schedulers.Schedulers;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;
import is.hi.hbvg601.team16.sportdemon.services.HomeService;
import is.hi.hbvg601.team16.sportdemon.services.WorkoutService;
import is.hi.hbvg601.team16.sportdemon.services.implementations.HomeServiceImplementation;
import is.hi.hbvg601.team16.sportdemon.services.implementations.WorkoutServiceImplementation;
import is.hi.hbvg601.team16.sportdemon.ui.workoutactivity.ExerciseComboRecyclerViewAdapter;

import dmax.dialog.SpotsDialog;

public class WorkoutActivity extends AppCompatActivity {

    private WorkoutService mWorkoutService;
    private HomeService mHomeService;

    // Intent code
    private static final int RESULT_SUCCESS = -1;
    private static final int RESULT_WORKOUT_FINISHED = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        this.mWorkoutService = new WorkoutServiceImplementation(this);
        this.mHomeService = new HomeServiceImplementation(this);

        Workout mWorkout = mHomeService.getCurrentWorkout();
        TextView title = findViewById(R.id.workout_title);
        title.setText(mWorkout.getTitle());

        // Setja upp RecyclerView fyrir ExerciseCombos
        RecyclerView mECRecyclerView = findViewById(R.id.workout_recyclerView);
        mECRecyclerView.setLayoutManager(new LinearLayoutManager(WorkoutActivity.this));

        ExerciseComboRecyclerViewAdapter adapter = new ExerciseComboRecyclerViewAdapter(
                WorkoutActivity.this, mWorkout.getExerciseComboList()
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
            ExerciseCombo newEC = new ExerciseCombo();
            newEC.setWorkout(mWorkout.getId());
            Intent i = new Intent(WorkoutActivity.this, ExerciseComboActivity.class);
            i.putExtra("EXERCISECOMBO", newEC);
            exerciseComboResultLauncher.launch(i);
        });

        findViewById(R.id.workout_remove_button).setOnClickListener( v -> {
            Button button = findViewById(R.id.workout_remove_button);
            if(!v.isActivated()) {
                button.setText(this.getString(R.string.cancel));
                v.setActivated(true);
                adapter.setClickListener(
                        (View v2, int position, List<ExerciseCombo> data) -> {
                            ExerciseCombo ec = data.get(position);

                            AlertDialog.Builder alert = new AlertDialog.Builder(this);
                            alert.setTitle("Delete \"" + ec.getTitle() + "\"?");
                            alert.setMessage("Are you sure you want to delete?");

                            alert.setPositiveButton("Yes", (dialog, which) -> {
                                SpotsDialog loadingDialog = new SpotsDialog(
                                        this,
                                        "Removing Exercise"
                                );
                                loadingDialog.show();

                                mWorkoutService.deleteEC(ec)
                                        .subscribeOn(Schedulers.io())
                                        .doOnComplete(() -> {
                                            // Local
                                            mHomeService.removeExerciseComboInCurrentWorkout(ec);

                                            Toast.makeText(WorkoutActivity.this,
                                                    "Exercise removed successfully",
                                                    Toast.LENGTH_LONG
                                            ).show();
                                            refreshList();
                                            loadingDialog.dismiss();
                                            dialog.dismiss();
                                        })
                                        .doOnError(error -> {
                                            Toast.makeText(WorkoutActivity.this,
                                                    error.getMessage(),
                                                    Toast.LENGTH_LONG
                                            ).show();
                                            loadingDialog.dismiss();
                                            dialog.dismiss();
                                        })
                                        .subscribe();

//                                Call<Void> callSync = mWorkoutService.deleteExerciseCombo(ec);
//                                callSync.enqueue(new Callback<Void>() {
//                                    @Override
//                                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
//                                        if (response.isSuccessful()) {
//                                            // Get response
//                                            try {
//                                                // Local
//                                                mHomeService.removeExerciseComboInCurrentWorkout(ec);
//                                                mHomeService.editCurrentWorkoutInUser();
//
//                                                Toast.makeText(WorkoutActivity.this,
//                                                        "Exercise removed successfully",
//                                                        Toast.LENGTH_LONG
//                                                ).show();
//
//                                                refreshList();
//
//                                                loadingDialog.dismiss();
//                                                dialog.dismiss();
//                                            } catch (Exception e) {
//                                                // UI
//                                                Toast.makeText(WorkoutActivity.this,
//                                                        e.toString(),
//                                                        Toast.LENGTH_LONG
//                                                ).show();
//                                                loadingDialog.dismiss();
//                                                dialog.dismiss();
//                                            }
//                                        } else {
//                                            Toast.makeText(WorkoutActivity.this,
//                                                    response.code()+" - "+ response,
//                                                    Toast.LENGTH_SHORT
//                                            ).show();
//                                            loadingDialog.dismiss();
//                                            dialog.dismiss();
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
//                                        Toast.makeText(WorkoutActivity.this,
//                                                t.toString(),
//                                                Toast.LENGTH_LONG
//                                        ).show();
//                                        loadingDialog.dismiss();
//                                        dialog.dismiss();
//                                    }
//                                });

                            });
                            alert.setNegativeButton("NO", (dialog, which) -> {
                                // close dialog
                                dialog.cancel();
                            });
                            alert.show();
                        });

                mECRecyclerView.setAdapter(adapter);
                mECRecyclerView.setBackgroundColor(Color.argb(31,255,0,0));
            } else {
                v.setActivated(false);
                button.setText(this.getString(R.string.remove));
                adapter.setClickListener(
                        (View v2, int position, List<ExerciseCombo> data) -> {
                            Intent i = new Intent(
                                    WorkoutActivity.this, ExerciseComboActivity.class
                            );
                            i.putExtra("EXERCISECOMBO", data.get(position));
                            exerciseComboResultLauncher.launch(i);
                        });
                mECRecyclerView.setAdapter(adapter);
                mECRecyclerView.setBackgroundColor(Color.TRANSPARENT);
            }
        });

        findViewById(R.id.workout_play_button).setOnClickListener( v -> {
            Intent i = new Intent(WorkoutActivity.this, PlayTrackerActivity.class);
            i.putExtra("WORKOUT", mHomeService.getCurrentWorkout());
            playTrackerResultLauncher.launch(i);
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHomeService = null;
        mWorkoutService = null;
    }

    private final ActivityResultLauncher<Intent> exerciseComboResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_SUCCESS) {
                    Intent data = result.getData();
                    assert data != null;

                    ExerciseCombo ec = (ExerciseCombo) data.getSerializableExtra("EXERCISECOMBO");
                    boolean edit = data.getBooleanExtra("EDIT", false);

                    if (edit) {
                        mHomeService.editExerciseComboInCurrentWorkout(ec);
                    } else {
                        mHomeService.addExerciseComboToCurrentWorkout(ec);
                    }
//                    mHomeService.editCurrentWorkoutInUser();
                }
                refreshList();
            }
    );

    private final ActivityResultLauncher<Intent> playTrackerResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_SUCCESS) {
                    Intent data = result.getData();
                    assert data != null : "No Workout Result data given";

                    // Inniheldur Workout Result á "WORKOUTRESULT"
                    setResult(RESULT_WORKOUT_FINISHED, data);
                    finish();
                } else {
                    setResult(RESULT_CANCELED);
                    finish();
                }
            }
    );

    private void refreshList() {
        RecyclerView mECRecyclerView = findViewById(R.id.workout_recyclerView);
        ExerciseComboRecyclerViewAdapter adapter =
                (ExerciseComboRecyclerViewAdapter) mECRecyclerView.getAdapter();
        assert adapter != null;
        Workout w = mHomeService.getCurrentWorkout();
        if(w != null) adapter.setData(w.getExerciseComboList());
        mECRecyclerView.setAdapter(adapter);
    }

}

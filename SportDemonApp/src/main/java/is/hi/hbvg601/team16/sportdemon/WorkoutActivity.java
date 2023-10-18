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

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
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

    private RecyclerView mECRecyclerView;
    private ExerciseComboRecyclerViewAdapter mAdapter;

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
        this.mECRecyclerView = findViewById(R.id.workout_recyclerView);
        mECRecyclerView.setLayoutManager(new LinearLayoutManager(WorkoutActivity.this));

        this.mAdapter = new ExerciseComboRecyclerViewAdapter(WorkoutActivity.this);
        mAdapter.setClickListener(
                (View v, int position, List<ExerciseCombo> data) -> {
                    Intent i = new Intent(
                            WorkoutActivity.this, ExerciseComboActivity.class
                    );
                    i.putExtra("EXERCISECOMBO", data.get(position));
                    exerciseComboResultLauncher.launch(i);
                });

        refreshList();

        findViewById(R.id.workout_add_button).setOnClickListener(v -> {
            ExerciseCombo newEC = new ExerciseCombo();
            newEC.setWorkoutID(mWorkout.getId());
            Intent i = new Intent(WorkoutActivity.this, ExerciseComboActivity.class);
            i.putExtra("EXERCISECOMBO", newEC);
            exerciseComboResultLauncher.launch(i);
        });

        findViewById(R.id.workout_remove_button).setOnClickListener( v -> {
            Button button = findViewById(R.id.workout_remove_button);
            if(!v.isActivated()) {
                button.setText(this.getString(R.string.cancel));
                v.setActivated(true);
                mAdapter.setClickListener(
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

                mECRecyclerView.setAdapter(mAdapter);
                mECRecyclerView.setBackgroundColor(Color.argb(31,255,0,0));
            } else {
                v.setActivated(false);
                button.setText(this.getString(R.string.remove));
                mAdapter.setClickListener(
                        (View v2, int position, List<ExerciseCombo> data) -> {
                            Intent i = new Intent(
                                    WorkoutActivity.this, ExerciseComboActivity.class
                            );
                            i.putExtra("EXERCISECOMBO", data.get(position));
                            exerciseComboResultLauncher.launch(i);
                        });
                mECRecyclerView.setAdapter(mAdapter);
                mECRecyclerView.setBackgroundColor(Color.TRANSPARENT);
            }
        });

        findViewById(R.id.workout_play_button).setOnClickListener( v -> {
            if(!mAdapter.getData().isEmpty()) {
                Intent i = new Intent(WorkoutActivity.this, PlayTrackerActivity.class);
                i.putExtra("WORKOUT", mHomeService.getCurrentWorkout());
                playTrackerResultLauncher.launch(i);
            } else {
                Toast.makeText(WorkoutActivity.this,
                        "Give the workout an exercise",
                        Toast.LENGTH_LONG
                ).show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mECRecyclerView = null;
        mAdapter = null;
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
        Workout w = mHomeService.getCurrentWorkout();

        mWorkoutService.getWorkoutWithEC(w.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(workoutWithEC -> {
                    mAdapter.setData(workoutWithEC.getExerciseComboList());
                    mECRecyclerView.setAdapter(mAdapter);
                })
                .doOnError(error -> Toast.makeText(WorkoutActivity.this,
                        error.getMessage(),
                        Toast.LENGTH_LONG
                ).show())
                .subscribe();
    }

}

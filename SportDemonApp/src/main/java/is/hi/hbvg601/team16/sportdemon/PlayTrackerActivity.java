package is.hi.hbvg601.team16.sportdemon;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.javatuples.Quartet;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.WorkoutResult;
import is.hi.hbvg601.team16.sportdemon.ui.playtrackeractivity.PlayTrackerRecyclerViewAdapter;


public class PlayTrackerActivity extends AppCompatActivity {

    private MediaPlayer mWhistle;
    private MediaPlayer mFinished;

    private boolean mStarted = false;
    private CountDownTimer mMainTimer;
    private long mTimeLeftInMillis;

    private Workout mWorkout;
    //               timer, exercise text, setsText, #exComboText
    private List<Quartet<Integer, String, String, String>> mTrackerList;
    private int mTrackerIndex;

    private Button mPlayBtn;
    private Button mBackBtn;
    private Button mForwardBtn;
    private TextView mWorkoutTitleText;
    private TextView mWorkText;
    private TextView mSetsText;
    private TextView mExComboText;

    private RecyclerView mRecyclerView;

    private ActivityResultLauncher<Uri> takePictureLauncher;
    private Uri mPhotoUri;

    // Codes
    private final int CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_tracker);

        mWorkout = (Workout) getIntent().getSerializableExtra("WORKOUT");
        assert mWorkout != null : "Workout is null";

        mWhistle = MediaPlayer.create(this, R.raw.whistle);
        mFinished = MediaPlayer.create(this, R.raw.finished);

        mRecyclerView = findViewById(R.id.tracker_recyclerView);
        mWorkoutTitleText = findViewById(R.id.workout_title);
        mPlayBtn = findViewById(R.id.tracker_play_button);
        mWorkText = findViewById(R.id.exerciseTitle);
        mSetsText = findViewById(R.id.sets);
        mExComboText = findViewById(R.id.excombo);
        mBackBtn = findViewById(R.id.tracker_button_back);
        mForwardBtn = findViewById(R.id.tracker_button_next);

        mWorkoutTitleText.setText(mWorkout.getTitle());

        // Búa til lista fyrir play tracker til að ítra yfir
        List<ExerciseCombo> ecl = mWorkout.getExerciseComboList();
        mTrackerList = new ArrayList<>();

        int ecIndex = 1;
        for (ExerciseCombo ec : ecl) {
            // Exercise Combo
            for (int i = 0; i < ec.getSets(); i++) {
                Optional<Integer> duration = Optional.of(ec.getDurationPerSet());
                int durationInt = duration.get() > -1 ? duration.get() : 0; // Set 0 if null or
                // negative
                String exerciseText = ec.getTitle() + ": ";
                if (durationInt > 0) {
                    exerciseText += String.format(
                            Locale.getDefault(),
                            "%02d:%02d",
                            durationInt / 60, durationInt % 60);
                } else {
                    exerciseText += ec.getReps() + " reps";
                }
                mTrackerList.add(new Quartet<>(durationInt, exerciseText,
                        i + 1 + "/" + ec.getSets(),
                        ecIndex + "/" + ecl.size()
                ));
            }

            // Rest between Exercise Combos
            Optional<Integer> rest = Optional.of(mWorkout.getRestBetweenEC());
            int restInt = rest.get() > -1 ? rest.get() : 0; // Set 0 if null or negative
            String restText = "Exercise Rest";
            mTrackerList.add(new Quartet<>(restInt, restText,
                    "",
                    ecIndex + "/" + ecl.size()
            ));
            ecIndex++;
        }
        // Remove last rest, not needed
        mTrackerList.remove(mTrackerList.size() - 1);

        PlayTrackerRecyclerViewAdapter adapter = new PlayTrackerRecyclerViewAdapter(
                this,
                mTrackerList.stream()
                        .map(Quartet::getValue1)
                        .collect(Collectors.toList())
        );
//        adapter.setClickListener();
        mRecyclerView.setAdapter(adapter);

        mPlayBtn.setOnClickListener(this::playWorkout);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWhistle != null) mWhistle.release();
        this.mWhistle = null;
        this.mMainTimer = null;
        this.mTrackerList = null;
        this.mPlayBtn = null;
        this.mBackBtn = null;
        this.mForwardBtn = null;
        this.mWorkoutTitleText = null;
        this.mWorkText = null;
        this.mSetsText = null;
        this.mExComboText = null;
        this.mRecyclerView = null;
    }

    @SuppressLint("InflateParams")
    private void playWorkout(View v) {

        if (!v.isActivated()) { // Play
            v.setActivated(true);
            mPlayBtn.setBackground(ResourcesCompat
                    .getDrawable(getResources(), R.drawable.pause_button, null));

            if (!mStarted) { // Initialize
                mTrackerIndex = 0;

                Dialog startDialog = new Dialog(this);
                startDialog.setContentView(getLayoutInflater()
                        .inflate(R.layout.dialog_start_workout, null));
                startDialog.setCancelable(false);
                startDialog.setTitle("Workout Starting");
                TextView timerText = startDialog.findViewById(R.id.startWorkoutTimerText);
                timerText.setText("5");
                startDialog.show();   //

                new CountDownTimer(5000, 1000) {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timerText.setText("" + (millisUntilFinished / 1000));
                    }

                    @Override
                    public void onFinish() {
                        mWhistle.start();
                        startDialog.dismiss();
                    }
                }.start();

                mStarted = true;

                Quartet<Integer, String, String, String> currentExerciseQuartet
                        = mTrackerList.get(mTrackerIndex);

                mTimeLeftInMillis = currentExerciseQuartet.getValue0() * 1000;
                mWorkText.setText(currentExerciseQuartet.getValue1());
                mSetsText.setText(currentExerciseQuartet.getValue2());
                mExComboText.setText(currentExerciseQuartet.getValue3());

                mBackBtn.setOnClickListener(null); // Nenni ekki að útfæra
                mForwardBtn.setOnClickListener(this::goToNextExercise);
            }

            if (mTimeLeftInMillis > 0) startTimer();

        } else {  // Pause
            if (mTimeLeftInMillis > 0) { // Disable if no ongoing timer
                v.setActivated(false);

                pauseTimer();

                mPlayBtn.setBackground(ResourcesCompat
                        .getDrawable(getResources(), R.drawable.play_button, null));
            }
        }
    }

    private void goToNextExercise(View v) {
        mTrackerIndex++;
        if (mTrackerIndex > mTrackerList.size()) workoutFinished();

        Quartet<Integer, String, String, String> currentExerciseQuartet
                = mTrackerList.get(mTrackerIndex);

        mRecyclerView.smoothScrollToPosition(mTrackerIndex);

        mTimeLeftInMillis = currentExerciseQuartet.getValue0() * 1000;
        mWorkText.setText(currentExerciseQuartet.getValue1());
        mSetsText.setText(currentExerciseQuartet.getValue2());
        mExComboText.setText(currentExerciseQuartet.getValue3());

        if (mTimeLeftInMillis > 0) startTimer();
    }

    private void startTimer() {
        mMainTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mWhistle.start();
                goToNextExercise(null);
            }
        }.start();

    }

    private void pauseTimer() {
        mMainTimer.cancel();
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes,
                seconds);

        mWorkText.setText(timeLeftFormatted);
    }

    private void workoutFinished() {
        mFinished.start();

        AtomicBoolean imageTaken = new AtomicBoolean(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View vAlert = getLayoutInflater().inflate(R.layout.dialog_finish_workout, null);
        builder.setView(vAlert);
        builder.setTitle("Save Workout Result?");

        TextView workoutResult = vAlert.findViewById(R.id.workoutResultText);
        FrameLayout frameLayout = vAlert.findViewById(R.id.resultPhotoFrame);
        ImageView photoView = vAlert.findViewById(R.id.takenPhoto);

        workoutResult.setText(mWorkout.toString());

        frameLayout.setOnClickListener(view -> {
            if (!imageTaken.get()) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    // Take Picture
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "New Picture");
                    values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
                    mPhotoUri = getContentResolver()
                            .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                    takePictureLauncher.launch(mPhotoUri);

                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{android.Manifest.permission.CAMERA},
                            CAMERA_PERMISSION_CODE);
                }
            }
        });

        takePictureLauncher =
                registerForActivityResult(new ActivityResultContracts.TakePicture(), result -> {
                    if (result) {
                        photoView.setImageURI(mPhotoUri);
                    }
                });

        builder.setPositiveButton("Save", (dialog, which) -> {
            WorkoutResult wr = new WorkoutResult(mWorkout.toString(), mPhotoUri);

            Intent skil = new Intent();
            skil.putExtra("WORKOUTRESULT", wr);
            setResult(RESULT_OK, skil);

            finish();
        });

        builder.setNegativeButton("Skip", (dialog, which) -> {
            setResult(RESULT_CANCELED);
            finish();
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                workoutFinished();
            } else {
                Toast.makeText(this,
                        "Camera permission required",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }

}

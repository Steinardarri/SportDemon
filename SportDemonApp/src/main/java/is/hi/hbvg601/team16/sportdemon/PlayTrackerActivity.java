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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.javatuples.Quartet;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import dmax.dialog.SpotsDialog;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.WorkoutResult;
import is.hi.hbvg601.team16.sportdemon.services.WorkoutService;
import is.hi.hbvg601.team16.sportdemon.services.implementations.WorkoutServiceImplementation;
import is.hi.hbvg601.team16.sportdemon.ui.playtrackeractivity.PlayTrackerRecyclerViewAdapter;


public class PlayTrackerActivity extends AppCompatActivity {

    private WorkoutService mWorkoutService;

    private MediaPlayer mWhistle;
    private MediaPlayer mFinished;

    private boolean mStarted = false;
    private boolean mPlaying = false;
    private CountDownTimer mMainTimer;
    private long mTimeLeftInMillis;

    private Workout mWorkout;

    //                 timer, exercise text, setsText, #exComboText
    private List<Quartet<Integer, String, String, String>> mTrackerList;
    private int mTrackerIndex = -1; // Index is incremented before being used

    private Button mPlayBtn;
    private Button mBackBtn;
    private Button mForwardBtn;
    private TextView mWorkoutTitleText;
    private TextView mWorkText;
    private TextView mSetsText;
    private TextView mExComboText;

    private RecyclerView mRecyclerView;

    private AlertDialog.Builder mFinishedDialogBuilder;
    private ActivityResultLauncher<Uri> mTakePictureLauncher;
    private Uri mPhotoUri;
    private ImageView mPhotoView;
    private final AtomicBoolean imageTaken = new AtomicBoolean(false);

    // Codes
    private final int CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_tracker);

        this.mWorkoutService = new WorkoutServiceImplementation(PlayTrackerActivity.this);

        mWorkout = (Workout) getIntent().getSerializableExtra("WORKOUT");

        assert mWorkout != null : "Workout is null";

        mWhistle = MediaPlayer.create(PlayTrackerActivity.this, R.raw.whistle);
        mFinished = MediaPlayer.create(PlayTrackerActivity.this, R.raw.finished);

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
        AtomicReference<List<ExerciseCombo>> arECL = new AtomicReference<>();
        mWorkoutService.getWorkoutWithEC(mWorkout.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(workoutWithEC ->
                    arECL.set(workoutWithEC.getExerciseComboList())
                )
                .doOnError(error -> Toast.makeText(PlayTrackerActivity.this,
                        error.getMessage(),
                        Toast.LENGTH_LONG
                ).show())
                .subscribe();

        List<ExerciseCombo> ecl = arECL.get();
        mTrackerList = new ArrayList<>();

        int ecIndex = 1;
        for (ExerciseCombo ec : ecl) {
            // Exercise Combo
            for (int i = 0; i < ec.getSets(); i++) {
                // Exercise
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

                // Set Rest
                if (i != ec.getSets()-1) { // Skip if last set
                    int restInt = ec.getRestBetweenSets();
                    String restText = "Set Rest";
                    mTrackerList.add(new Quartet<>(restInt, restText,
                            i + 1 + "/" + ec.getSets(),
                            ecIndex + "/" + ecl.size()
                    ));
                }
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

        // Set up RecycleView
        List<String> rows = mTrackerList.stream()
                .map(Quartet::getValue1)
                .collect(Collectors.toList());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(PlayTrackerActivity.this));
        PlayTrackerRecyclerViewAdapter adapter = new PlayTrackerRecyclerViewAdapter(
                PlayTrackerActivity.this, rows
        );
        adapter.setClickListener(null);
        mRecyclerView.setAdapter(adapter);

        Quartet<Integer, String, String, String> firstExerciseQuartet
                = mTrackerList.get(0);

        mTimeLeftInMillis = firstExerciseQuartet.getValue0() * 1000;
        mWorkText.setText("");
        mSetsText.setText(firstExerciseQuartet.getValue2());
        mExComboText.setText(firstExerciseQuartet.getValue3());

        mPlayBtn.setOnClickListener(this::playWorkout);

        // Workout Result Dialog
        mFinishedDialogBuilder = new AlertDialog.Builder(PlayTrackerActivity.this);

        mTakePictureLauncher =
                registerForActivityResult(new ActivityResultContracts.TakePicture(), result -> {
                    if (result) {
                        mPhotoView.setImageURI(mPhotoUri);
                        imageTaken.set(true);
                    }
                });
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

        if (!mPlaying) { // Play
            mPlaying = true;
            mPlayBtn.setBackground(ResourcesCompat
                    .getDrawable(getResources(), R.drawable.pause_button, null));

            if (!mStarted) { // Initialize

                Dialog startDialog = new Dialog(PlayTrackerActivity.this);
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
                        mStarted = true;
                        goToNextExercise(null);
                    }
                }.start();

                mBackBtn.setOnClickListener(null); // Nenni ekki að útfæra
                mForwardBtn.setOnClickListener(this::goToNextExercise);
            } else {
                if (mTimeLeftInMillis > 0) startTimer();
            }
        } else {  // Pause
            if (mTimeLeftInMillis > 0) { // Disable if no ongoing timer
                mPlaying = false;

                pauseTimer();

                mPlayBtn.setBackground(ResourcesCompat
                        .getDrawable(getResources(), R.drawable.play_button, null));
            }
        }
    }

    private void goToNextExercise(View v) {
        if (mMainTimer != null) pauseTimer();
        mTrackerIndex++;
        if (mTrackerIndex >= mTrackerList.size()) workoutFinished(true);
        else {
            Quartet<Integer, String, String, String> currentExerciseQuartet
                    = mTrackerList.get(mTrackerIndex);

            mRecyclerView.smoothScrollToPosition(mTrackerIndex);
            PlayTrackerRecyclerViewAdapter adapter =
                    (PlayTrackerRecyclerViewAdapter) mRecyclerView.getAdapter();
            assert adapter != null;
            adapter.setSelectedIndex(mTrackerIndex);

            mTimeLeftInMillis = currentExerciseQuartet.getValue0() * 1000;
            mWorkText.setText(currentExerciseQuartet.getValue1());
            mSetsText.setText(currentExerciseQuartet.getValue2());
            mExComboText.setText(currentExerciseQuartet.getValue3());

            if (mTimeLeftInMillis > 0) startTimer();
        }
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
        // Ceil to next second
        mTimeLeftInMillis = (long) Math.ceil(mTimeLeftInMillis / 1000.0) * 1000;
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes,
                seconds);

        mWorkText.setText(timeLeftFormatted);
    }

    private void workoutFinished(boolean playSound) {
        if (playSound) mFinished.start();

        mPlayBtn.setOnClickListener(null);
        mBackBtn.setOnClickListener(null);
        mForwardBtn.setOnClickListener(null);

        View vAlert = getLayoutInflater().inflate(R.layout.dialog_finish_workout, null);
        mFinishedDialogBuilder.setView(vAlert);
        mFinishedDialogBuilder.setTitle("Save Workout Result?");

        TextView workoutResult = vAlert.findViewById(R.id.workoutResultText);
        FrameLayout frameLayout = vAlert.findViewById(R.id.resultPhotoFrame);
        mPhotoView = vAlert.findViewById(R.id.takenPhoto);

        workoutResult.setText(mWorkout.toString());

        frameLayout.setOnClickListener(view -> {
            if (!imageTaken.get()) {
                if (ContextCompat.checkSelfPermission(
                        PlayTrackerActivity.this,
                        android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    // Take Picture
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "New Picture");
                    values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
                    mPhotoUri = getContentResolver()
                            .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                    mTakePictureLauncher.launch(mPhotoUri);

                } else {
                    ActivityCompat.requestPermissions(PlayTrackerActivity.this,
                            new String[]{android.Manifest.permission.CAMERA},
                            CAMERA_PERMISSION_CODE);
                }
            }
        });

        mFinishedDialogBuilder.setPositiveButton("Save", (dialog, which) -> {
            SpotsDialog loadingDialog = new SpotsDialog(this, "Preparing Data");
            loadingDialog.show();

            // Get photo data to save
            InputStream iStream;
            byte[] photo;
            try {
                iStream = getContentResolver().openInputStream(mPhotoUri);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                assert iStream != null;
                photo = getBytes(iStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            WorkoutResult wr = new WorkoutResult(mWorkout.toString(), photo);

            Intent skil = new Intent();
            skil.putExtra("WORKOUTRESULT", wr);
            setResult(RESULT_OK, skil);

            loadingDialog.dismiss();
            finish();
        });

        mFinishedDialogBuilder.setNegativeButton("Skip", (dialog, which) -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        mFinishedDialogBuilder.show();

        if (!playSound) frameLayout.performClick();
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                workoutFinished(false);
            } else {
                Toast.makeText(PlayTrackerActivity.this,
                        "Camera permission required",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }

}

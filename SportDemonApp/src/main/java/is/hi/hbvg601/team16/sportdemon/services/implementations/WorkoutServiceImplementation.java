package is.hi.hbvg601.team16.sportdemon.services.implementations;

import android.content.Context;

import androidx.room.Room;

import java.util.List;
import java.util.UUID;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import is.hi.hbvg601.team16.sportdemon.persistence.SportDemonDatabase;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.WorkoutResult;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.transaction.WorkoutWithEC;
import is.hi.hbvg601.team16.sportdemon.services.WorkoutService;

public class WorkoutServiceImplementation implements WorkoutService {

    SportDemonDatabase db;

    public WorkoutServiceImplementation(Context context){
        this.db = Room.databaseBuilder(context,
                SportDemonDatabase.class, "SportDemonDatabase").build();
    }

    // Workout

    @Override
    public Single<Workout> saveWorkout(Workout workout) {
        return db.workoutDAO().insertWorkout(workout);
    }

    @Override
    public Completable deleteWorkout(Workout workout) {
        return db.workoutDAO().deleteWorkout(workout);
    }

    @Override
    public Flowable<List<Workout>> getAllWorkouts() {
        return db.workoutDAO().getWorkouts();
    }

    @Override
    public Single<WorkoutWithEC> getWorkoutWithEC(UUID id) {
        return db.workoutDAO().getWorkoutWithEC(id);
    }

    // ExerciseCombo

    @Override
    public Single<ExerciseCombo> saveExerciseCombo(ExerciseCombo ec) {
        return db.exerciseComboDAO().insertExerciseCombo(ec);
    }

    @Override
    public Completable deleteEC(ExerciseCombo ec) {
        return db.exerciseComboDAO().deleteExerciseCombo(ec);
    }

    // Workout Result

    @Override
    public Single<WorkoutResult> saveWorkoutResult(WorkoutResult wr) {
        return db.workoutResultDAO().insertWorkoutResult(wr);
    }

}

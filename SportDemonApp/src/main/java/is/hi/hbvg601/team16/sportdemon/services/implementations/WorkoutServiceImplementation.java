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
import is.hi.hbvg601.team16.sportdemon.services.WorkoutService;

import retrofit2.Call;

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
    public Call<Void> updateWorkout(Workout workout) {
        return nmAPI.updateWorkout(workout);
    }

    @Override
    public Completable deleteWorkout(Workout workout) {
        return db.workoutDAO().deleteWorkout(workout);
    }

    @Override
    public Call<Workout> findWorkoutByID(UUID id) {
        return nmAPI.findWorkoutByID(id);
    }

    @Override
    public Flowable<List<Workout>> getAllWorkouts() {
        return db.workoutDAO().getWorkouts();
    }

    // ExerciseCombo

    @Override
    public Call<ExerciseCombo> saveExerciseCombo(ExerciseCombo ec) {
        return nmAPI.addExerciseCombo(ec);
    }

    @Override
    public Call<Void> updateExerciseCombo(ExerciseCombo exerciseCombo) {
        return nmAPI.updateExerciseCombo(exerciseCombo);
    }

    @Override
    public Call<Void> deleteExerciseCombo(ExerciseCombo ec) {
        return nmAPI.deleteExerciseCombo(ec.getId());
    }

    // Workout Result

    @Override
    public Call<WorkoutResult> findWorkoutResultByID(UUID id) {
        return nmAPI.getWorkoutResult(id);
    }

    @Override
    public Single<WorkoutResult> saveWorkoutResult(WorkoutResult wr) {
        return db.workoutResultDAO().insertWorkoutResult(wr);
    }

}

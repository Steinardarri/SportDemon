package is.hi.hbvg601.team16.sportdemon.services;

import java.util.List;
import java.util.UUID;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.WorkoutResult;
import retrofit2.Call;

public interface WorkoutService {

    // Workout

    Single<Workout> saveWorkout(Workout workout);

    Call<Void> updateWorkout(Workout workout);

    Completable deleteWorkout(Workout workout);

    Call<Workout> findWorkoutByID(UUID id);

    Flowable<List<Workout>> getAllWorkouts();

    // Exercise

    Call<ExerciseCombo> saveExerciseCombo(ExerciseCombo ec);

    Call<Void> updateExerciseCombo(ExerciseCombo ec);

    Call<Void> deleteExerciseCombo(ExerciseCombo ec);

    // Workout Result

    Call<WorkoutResult> findWorkoutResultByID(UUID id);

    Single<WorkoutResult> saveWorkoutResult(WorkoutResult wr);

}

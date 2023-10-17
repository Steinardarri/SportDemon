package is.hi.hbvg601.team16.sportdemon.services;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.WorkoutResult;

public interface WorkoutService {

    // Workout

    Single<Workout> saveWorkout(Workout workout);

    Completable deleteWorkout(Workout workout);

    Flowable<List<Workout>> getAllWorkouts();

    // Exercise

    Single<ExerciseCombo> saveExerciseCombo(ExerciseCombo ec);

    Completable deleteEC(ExerciseCombo ec);

    // Workout Result

    Single<WorkoutResult> saveWorkoutResult(WorkoutResult wr);

}

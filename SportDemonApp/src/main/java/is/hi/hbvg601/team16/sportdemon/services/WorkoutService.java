package is.hi.hbvg601.team16.sportdemon.services;

import java.util.List;
import java.util.UUID;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.WorkoutResult;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.transaction.WorkoutWithEC;

public interface WorkoutService {

    // Workout

    Completable saveWorkout(Workout workout);

    Completable deleteWorkout(Workout workout);

    Flowable<List<Workout>> getAllWorkouts();

    Single<WorkoutWithEC> getWorkoutWithEC(UUID id);

    // Exercise

    Completable saveExerciseCombo(ExerciseCombo ec);

    Completable deleteEC(ExerciseCombo ec);

    // Workout Result

    Completable saveWorkoutResult(WorkoutResult wr);

}

package is.hi.hbvg601.team16.sportdemon.services;

import java.util.UUID;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.WorkoutResult;

import retrofit2.Call;

public interface WorkoutService {

    Call<Workout> saveWorkout(Workout workout);

    Call<Void> deleteWorkout(UUID id);

    Call<ExerciseCombo> saveExerciseCombo(ExerciseCombo ec);

    Call<Void> removeExerciseCombo(ExerciseCombo ec);

    Call<WorkoutResult> saveWorkoutResult(WorkoutResult wr);

    Call<Void> deleteWorkoutResult(UUID id);

    Call<Workout> findWorkoutByID(UUID id);

    Call<Void> updateWorkout(Workout workout);

    Call<Void> updateExerciseCombo(ExerciseCombo exerciseCombo);

}

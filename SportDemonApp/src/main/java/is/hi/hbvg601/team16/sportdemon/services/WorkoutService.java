package is.hi.hbvg601.team16.sportdemon.services;

import java.util.UUID;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.WorkoutResult;
import retrofit2.Call;

public interface WorkoutService {

    // Workout

    Call<Workout> saveWorkout(Workout workout);

    Call<Void> updateWorkout(Workout workout);

    Call<Void> deleteWorkout(Workout workout);

    Call<Workout> findWorkoutByID(UUID id);

    // Exercise

    Call<ExerciseCombo> saveExerciseCombo(ExerciseCombo ec);

    Call<Void> updateExerciseCombo(ExerciseCombo ec);

    Call<Void> deleteExerciseCombo(ExerciseCombo ec);

    // Workout Result

    Call<WorkoutResult> findWorkoutResultByID(UUID id);

    Call<WorkoutResult> saveWorkoutResult(WorkoutResult wr);

}

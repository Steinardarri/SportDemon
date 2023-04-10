package is.hi.hbvg601.team16.sportdemon.services;

import java.util.UUID;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.WorkoutResult;

import retrofit2.Call;

public interface WorkoutService {

    /**
     * @param workout to add to user
     * @return execute result
     */
    Call<Workout> saveWorkout(Workout workout);

    /**
     * @param id of workout to delete
     * @return execute result
     */
    String deleteWorkout(UUID id);

    /**
     * @param ec to add to workout
     * @return execution call to server
     */
    Call<ExerciseCombo> saveExerciseCombo(ExerciseCombo ec);

    /**
     * @param ec to remove from workout
     * @param workout to remove ec from
     * @return execute result
     */
    String removeExerciseCombo(ExerciseCombo ec, Workout workout);

    /**
     * @param wr to save
     * @param user to add workout result to
     * @return execute result
     */
    String saveWorkoutResult(WorkoutResult wr, User user);

    /**
     * @param id of workout result to remove
     * @return execute result
     */
    String deleteWorkoutResult(UUID id);

    /**
     * @param id of workout to get
     * @return workout entity
     */
    Workout findWorkoutByID(UUID id);
}

package is.hi.hbvg601.team16.sportdemon.services;

import java.util.List;
import java.util.UUID;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.WorkoutResult;

public interface WorkoutService {

    /**
     * @param workout to add to user
     * @param user to add workout to
     * @return execute result
     */
    String addWorkout(Workout workout, User user);

    /**
     * @param workout to edit, has to already exist
     * @return execute result
     */
    String editWorkout(Workout workout);

    /**
     * @param id of workout to delete
     * @return execute result
     */
    String deleteWorkout(UUID id);

    /**
     * @param ec to add to workout
     * @param workout to add ec to
     * @return execute result
     */
    String addExerciseCombo(ExerciseCombo ec, Workout workout);

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

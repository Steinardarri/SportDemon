package is.hi.hbvg601.team16.sportdemon.services;

import android.content.Context;

import java.util.List;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.WorkoutResult;

public interface HomeService {

    /**
     * @return current saved user
     */
    User getCurrentUser();

    void setCurrentUser(User user);

    Workout getCurrentWorkout();

    void setCurrentWorkout(Workout workout);

    void addExerciseComboToCurrentWorkout(ExerciseCombo ec);

    void editExerciseComboInCurrentWorkout(ExerciseCombo ec);

    void removeExerciseComboInCurrentWorkout(ExerciseCombo ec);

    void addWorkoutToUser(Workout workout);

    void editCurrentWorkoutInUser();

    void removeWorkoutFromUser(Workout workout);

    void addWorkoutResultToUser(WorkoutResult wr);

    void removeWorkoutResultFromUser(WorkoutResult wr);

}

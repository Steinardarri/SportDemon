package is.hi.hbvg601.team16.sportdemon.services;

import android.content.Context;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;

public interface HomeService {

    /**
     * @param  context to bind from
     * @return current saved user
     */
    User getCurrentUser(Context context);

    void setCurrentUser(User user, Context context);

    Workout getCurrentWorkout(Context context);

    void setCurrentWorkout(Workout workout, Context context);

    void addExerciseComboToCurrentWorkout(ExerciseCombo ec, Context context);

    void editExerciseComboInCurrentWorkout(ExerciseCombo ec, Context context);

    void removeExerciseComboInCurrentWorkout(ExerciseCombo ec, Context context);

    void addWorkoutToUser(Workout workout, Context context);

    void editCurrentWorkoutInUser(Context context);

    void removeWorkoutFromUser(Workout workout, Context context);

}

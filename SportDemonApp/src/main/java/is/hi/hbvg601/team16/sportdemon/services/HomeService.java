package is.hi.hbvg601.team16.sportdemon.services;

import android.content.Context;

import java.util.List;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.WorkoutResult;

public interface HomeService {

    /**
     * @param  context to bind from
     * @return current saved user
     */
    User getCurrentUser(Context context);
    /**
     * @param user to set as current
     * @param context to bind from
     */
    void setCurrentUser(User user, Context context);

    /**
     * @param  context to bind from
     * @return current saved workout
     */
    Workout getCurrentWorkout(Context context);
    /**
     * @param workout to set as current
     * @param context to bind from
     */
    void setCurrentWorkout(Workout workout, Context context);
}
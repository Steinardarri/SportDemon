package is.hi.hbvg601.team16.sportdemon.services;

import java.util.List;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.WorkoutResult;

public interface HomeService {

    /**
     * @param user get workout list from
     * @return list of workouts
     */
    List<Workout> getWorkouts(User user);

    /**
     * @param user to get workout result list from
     * @return list of workout results
     */
    List<WorkoutResult> getWorkoutResults(User user);
}
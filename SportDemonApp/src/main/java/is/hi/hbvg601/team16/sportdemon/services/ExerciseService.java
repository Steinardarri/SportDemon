package is.hi.hbvg601.team16.sportdemon.services;

import java.util.List;
import java.util.UUID;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.Exercise;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;

public interface ExerciseService {

    /**
     * @param  exercise to save to user
     * @param  user to save exercise to
     * @return execute result
     */
    String addExercise(Exercise exercise, User user);

    /**
     * @param  exercise to edit
     * @return execute result
     */
    String editExercise(Exercise exercise);

    /**
     * @param  id of exercise to remove
     * @return execute result
     */
    String removeExercise(UUID id);

    /**
     * @param  user to get exercise list from
     * @return list of exercises
     */
    List<Exercise> getExercises(User user);

    /**
     * @param id of exercise to get
     * @return exercise entity
     */
    Exercise findExerciseByID(UUID id);
}

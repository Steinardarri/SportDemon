package is.hi.hbvg601.team16.sportdemon.services.implementations;

import java.util.List;
import java.util.UUID;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.Exercise;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import is.hi.hbvg601.team16.sportdemon.services.ExerciseService;

public class ExerciseServiceImplementation implements ExerciseService {

    private final NetworkManagerAPI nmAPI;

    public ExerciseServiceImplementation(NetworkManagerAPI networkManager){
        this.nmAPI = networkManager;
    }

    @Override
    public String addExercise(Exercise exercise, User user) {
        return null;
    }

    @Override
    public String editExercise(Exercise exercise) {
        return null;
    }

    @Override
    public String removeExercise(UUID id) {
        return null;
    }

    @Override
    public List<Exercise> getExercises(User user) {
        return null;
    }

    @Override
    public Exercise findExerciseByID(UUID id) {
        return null;
    }
}

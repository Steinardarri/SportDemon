package is.hi.hbvg601.team16.sportdemon.services.implementations;

import java.util.UUID;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.WorkoutResult;
import is.hi.hbvg601.team16.sportdemon.services.WorkoutService;

import retrofit2.Call;

public class WorkoutServiceImplementation implements WorkoutService {

    private final NetworkManagerAPI nmAPI;

    public WorkoutServiceImplementation(NetworkManagerAPI networkManager){
        this.nmAPI = networkManager;
    }

    @Override
    public Call<Workout> saveWorkout(Workout workout) {
        return nmAPI.addWorkout(workout);
    }

    @Override
    public String deleteWorkout(UUID id) {
        return null;
    }

    @Override
    public Call<ExerciseCombo> saveExerciseCombo(ExerciseCombo ec) {
        return nmAPI.addExerciseCombo(ec);
    }

    @Override
    public String removeExerciseCombo(ExerciseCombo ec, Workout workout) {
        return null;
    }

    @Override
    public String saveWorkoutResult(WorkoutResult wr, User user) {
        return null;
    }

    @Override
    public String deleteWorkoutResult(UUID id) {
        return null;
    }

    @Override
    public Workout findWorkoutByID(UUID id) {
        return null;
    }
}

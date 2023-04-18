package is.hi.hbvg601.team16.sportdemon.services.implementations;

import java.util.UUID;

import io.reactivex.rxjava3.core.Observable;
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

    // Workout

    @Override
    public Call<Workout> saveWorkout(Workout workout) {
        return nmAPI.addWorkout(workout);
    }

    @Override
    public Call<Void> updateWorkout(Workout workout) {
        return nmAPI.updateWorkout(workout);
    }

    @Override
    public Call<Void> deleteWorkout(UUID id) {
        return null;
    }

    @Override
    public Call<Workout> findWorkoutByID(UUID id) {
        return nmAPI.findWorkoutByID(id);
    }

    // ExerciseCombo

    @Override
    public Call<ExerciseCombo> saveExerciseCombo(ExerciseCombo ec) {
        return nmAPI.addExerciseCombo(ec);
    }

    @Override
    public Call<Void> updateExerciseCombo(ExerciseCombo exerciseCombo) {
        return nmAPI.updateExerciseCombo(exerciseCombo);
    }

    @Override
    public Call<Void> removeExerciseCombo(ExerciseCombo ec, Workout workout) {
        return null;
    }

    // WorkoutResult

    @Override
    public Call<WorkoutResult> saveWorkoutResult(WorkoutResult wr, User user) {
        return null;
    }

    @Override
    public Call<Void> deleteWorkoutResult(UUID id) {
        return null;
    }

}

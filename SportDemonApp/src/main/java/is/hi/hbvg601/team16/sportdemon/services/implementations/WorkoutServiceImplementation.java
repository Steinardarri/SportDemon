package is.hi.hbvg601.team16.sportdemon.services.implementations;

import java.util.UUID;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;
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
    public Call<Void> deleteWorkout(Workout workout) {
        return nmAPI.deleteWorkout(workout.getId());
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
    public Call<Void> deleteExerciseCombo(ExerciseCombo ec) {
        return nmAPI.deleteExerciseCombo(ec.getId());
    }

    // Workout Result

    @Override
    public Call<WorkoutResult> findWorkoutResultByID(UUID id) {
        return nmAPI.getWorkoutResult(id);
    }

    @Override
    public Call<WorkoutResult> saveWorkoutResult(WorkoutResult wr) {
        return nmAPI.addWorkoutResult(wr);
    }

}

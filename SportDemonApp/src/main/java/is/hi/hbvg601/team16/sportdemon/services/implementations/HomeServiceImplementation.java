package is.hi.hbvg601.team16.sportdemon.services.implementations;

import android.content.Context;

import java.util.List;

import is.hi.hbvg601.team16.sportdemon.SportDemon;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.WorkoutResult;
import is.hi.hbvg601.team16.sportdemon.services.HomeService;

public class HomeServiceImplementation implements HomeService {

    SportDemon data;

    public HomeServiceImplementation(Context context){
        this.data = new SportDemon(context);
    }

    /**
     * @return current saved user
     */
    @Override
    public User getCurrentUser() {
        return data.getCurrentUser();
    }

    /**
     * @param user to set as current
     */
    @Override
    public void setCurrentUser(User user) {
        data.setCurrentUser(user);
    }

    /**
     * @return current saved Workout, with EC list
     */
    @Override
    public Workout getCurrentWorkout() {
        return data.getCurrentWorkout();
    }

    /**
     * @param workout to set as current, also saves it's EC list
     */
    @Override
    public void setCurrentWorkout(Workout workout) {
        data.setCurrentWorkout(workout);
    }

    @Override
    public void addExerciseComboToCurrentWorkout(ExerciseCombo ec) {
        Workout w = data.getCurrentWorkout();
        w.addExerciseCombo(ec);
        data.setCurrentWorkout(w);
    }

    @Override
    public void editExerciseComboInCurrentWorkout(ExerciseCombo newEC) {
        Workout w = data.getCurrentWorkout();
        w.editExerciseCombo(newEC);
        data.setCurrentWorkout(w);
    }

    @Override
    public void removeExerciseComboInCurrentWorkout(ExerciseCombo ec) {
        Workout w = data.getCurrentWorkout();
        w.removeExerciseCombo(ec);
        data.setCurrentWorkout(w);
    }

    @Override
    public void addWorkoutToUser(Workout workout) {
        User u = data.getCurrentUser();
        u.addWorkout(workout);
        data.setCurrentUser(u);
    }

    @Override
    public void editCurrentWorkoutInUser() {
        User u = data.getCurrentUser();
        Workout w = data.getCurrentWorkout();
        u.editWorkout(w);
        data.setCurrentUser(u);
    }

    @Override
    public void removeWorkoutFromUser(Workout workout) {
        User u = data.getCurrentUser();
        u.removeWorkout(workout);
        data.setCurrentUser(u);
    }

    @Override
    public void addWorkoutResultToUser(WorkoutResult wr) {
        User u = data.getCurrentUser();
        u.addWorkoutResult(wr);
        data.setCurrentUser(u);
    }

    @Override
    public void removeWorkoutResultFromUser(WorkoutResult wr) {
        User u = data.getCurrentUser();
        u.removeWorkoutResult(wr);
        data.setCurrentUser(u);
    }

}

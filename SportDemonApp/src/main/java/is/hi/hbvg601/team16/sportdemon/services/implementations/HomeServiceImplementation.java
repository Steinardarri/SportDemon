package is.hi.hbvg601.team16.sportdemon.services.implementations;

import android.content.Context;

import java.util.List;

import is.hi.hbvg601.team16.sportdemon.SportDemon;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;
import is.hi.hbvg601.team16.sportdemon.services.HomeService;

public class HomeServiceImplementation implements HomeService {

    private final NetworkManagerAPI nmAPI;

    public HomeServiceImplementation(NetworkManagerAPI networkManager){
        this.nmAPI = networkManager;
    }

    /**
     * @param  context to bind from
     * @return current saved user
     */
    @Override
    public User getCurrentUser(Context context) {
        SportDemon data = new SportDemon(context);
        return data.getCurrentUser();
    }

    /**
     * @param user to set as current
     * @param context to bind from
     */
    @Override
    public void setCurrentUser(User user, Context context) {
        SportDemon data = new SportDemon(context);
        data.setCurrentUser(user);
    }

    /**
     * @param  context to bind from
     * @return current saved Workout, with EC list
     */
    @Override
    public Workout getCurrentWorkout(Context context) {
        SportDemon data = new SportDemon(context);
        Workout w = data.getCurrentWorkout();
        // Þarf vegna exerciseCombo list er transient
        w.setExerciseCombo(data.getCurrentExerciseCombo());
        return w;
    }

    /**
     * @param workout to set as current, also saves it's EC list
     * @param context to bind from
     */
    @Override
    public void setCurrentWorkout(Workout workout, Context context) {
        SportDemon data = new SportDemon(context);
        data.setCurrentWorkout(workout);
        // Þarf vegna exerciseCombo list er transient
        if(workout != null) data.setCurrentExerciseCombo(workout.getExerciseCombo());
    }
}

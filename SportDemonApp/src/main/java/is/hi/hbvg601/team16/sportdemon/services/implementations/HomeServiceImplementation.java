package is.hi.hbvg601.team16.sportdemon.services.implementations;

import android.content.Context;

import is.hi.hbvg601.team16.sportdemon.SportDemon;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;
import is.hi.hbvg601.team16.sportdemon.services.HomeService;

public class HomeServiceImplementation implements HomeService {

    SportDemon data;

    public HomeServiceImplementation(Context context){
        this.data = new SportDemon(context);
    }

    /**
     * @return current saved Workout
     */
    @Override
    public Workout getCurrentWorkout() {
        return data.getCurrentWorkout();
    }

    /**
     * @param workout to set as current
     */
    @Override
    public void setCurrentWorkout(Workout workout) {
        data.setCurrentWorkout(workout);
    }

}

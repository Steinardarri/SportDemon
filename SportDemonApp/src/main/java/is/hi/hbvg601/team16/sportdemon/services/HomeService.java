package is.hi.hbvg601.team16.sportdemon.services;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;

public interface HomeService {

    Workout getCurrentWorkout();

    void setCurrentWorkout(Workout workout);

}

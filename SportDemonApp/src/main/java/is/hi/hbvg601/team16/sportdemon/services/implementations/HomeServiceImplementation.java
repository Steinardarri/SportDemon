package is.hi.hbvg601.team16.sportdemon.services.implementations;

import java.util.List;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.WorkoutResult;
import is.hi.hbvg601.team16.sportdemon.services.HomeService;

public class HomeServiceImplementation implements HomeService {

    private final NetworkManagerAPI nmAPI;

    public HomeServiceImplementation(NetworkManagerAPI networkManager){
        this.nmAPI = networkManager;
    }

    @Override
    public List<Workout> getWorkouts(User user) {
        return null;
    }

    @Override
    public List<WorkoutResult> getWorkoutResults(User user) {
        return null;
    }
}

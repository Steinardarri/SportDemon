package is.hi.hbvg601.team16.sportdemon.services.implementations;

import is.hi.hbvg601.team16.sportdemon.services.NetworkManager;
import is.hi.hbvg601.team16.sportdemon.services.WorkoutService;

public abstract class WorkoutServiceImplementation implements WorkoutService {

    private final NetworkManager mNetworkManager;

    public WorkoutServiceImplementation(NetworkManager networkManager){
        this.mNetworkManager = networkManager;
    }

}

package is.hi.hbvg601.team16.sportdemon.services.implementations;

import is.hi.hbvg601.team16.sportdemon.services.ExerciseService;
import is.hi.hbvg601.team16.sportdemon.services.NetworkManager;

public abstract class ExerciseServiceImplementation implements ExerciseService {

    private final NetworkManager mNetworkManager;

    public ExerciseServiceImplementation(NetworkManager networkManager){
        this.mNetworkManager = networkManager;
    }

}

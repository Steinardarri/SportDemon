package is.hi.hbvg601.team16.sportdemon.services.implementations;

import is.hi.hbvg601.team16.sportdemon.services.HomeService;
import is.hi.hbvg601.team16.sportdemon.services.NetworkManager;

public abstract class HomeServiceImplementation implements HomeService {

    private final NetworkManager mNetworkManager;

    public HomeServiceImplementation(NetworkManager networkManager){
        this.mNetworkManager = networkManager;
    }

}

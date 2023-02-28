package is.hi.hbvg601.team16.sportdemon.services.implementations;

import is.hi.hbvg601.team16.sportdemon.services.NetworkManager;
import is.hi.hbvg601.team16.sportdemon.services.UserService;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;

public abstract class UserServiceImplementation implements UserService {

    private final NetworkManager mNetworkManager;

    private User mUser;

    public UserServiceImplementation(NetworkManager networkManager){
        this.mNetworkManager = networkManager;
    }

    /**
     * Basic save service.
     *
     * @param user the user that is to be saved
     * @return boolean success
     */
    public String createAccount(User user) {
//        Call<User> response = mNetworkManager.createAccount(user);

        this.mUser = user;

        return "Success";
    }
}

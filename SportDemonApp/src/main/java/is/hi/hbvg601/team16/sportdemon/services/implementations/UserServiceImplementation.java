package is.hi.hbvg601.team16.sportdemon.services.implementations;

import java.util.UUID;

import is.hi.hbvg601.team16.sportdemon.services.UserService;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;

public class UserServiceImplementation implements UserService {

    private final NetworkManagerAPI nmAPI;

    private User mUser;

    public UserServiceImplementation(NetworkManagerAPI networkManager){
        this.nmAPI = networkManager;
    }

    /**
     * @param  id of the user to get
     * @return User data of the id
     */
    @Override
    public User findUserByID(UUID id) {
        User returnUser = nmAPI.getUser(id);
        // returnUser has possibly null values, when not found
        return returnUser;
    }

    /**
     * @param  username of the user to get
     * @return User data of the username
     */
    @Override
    public User findUserByUsername(String username) {
        User returnUser = nmAPI.getUser(username);
        // returnUser has possibly null values, when not found
        return returnUser;
    }

    @Override
    public boolean isLoggedIn(User user) {
        return false;
    }

    /**
     * @param  user the user that is to be saved
     * @return String success status
     */
    @Override
    public String createAccount(User user) {
        return nmAPI.createAccount(user);
    }

    @Override
    public String editAccount(User user) {
        return null;
    }

    @Override
    public String deleteAccount(UUID id) {
        return null;
    }

    /**
     * @param  username of the account to login
     * @param  password of the account to login
     * @return User of the account, void if not correct
     */
    @Override
    public User login(String username, String password) {
        User doesExist = findUserByUsername(username);
        if(doesExist != null){
            if(doesExist.getPassword().equals(password)){
                return doesExist;
            }
        }
        return null;
    }

    @Override
    public Boolean logout() {
        return null;
    }

    // TODO: Taka út þegar server komið inn
    public User getUser() {
        return mUser;
    }
}

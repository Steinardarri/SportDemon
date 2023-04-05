package is.hi.hbvg601.team16.sportdemon.services.implementations;

import java.util.UUID;

import is.hi.hbvg601.team16.sportdemon.services.UserService;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import retrofit2.Call;

public class UserServiceImplementation implements UserService {

    private final NetworkManagerAPI nmAPI;

    public void dispose() {this.nmAPI.dispose();}

    public UserServiceImplementation(NetworkManagerAPI networkManager){
        this.nmAPI = networkManager;
    }

    /**
     * @param  id of the user to get
     * @return User data of the id
     */
    @Override
    public User findUserByID(UUID id) {
        return nmAPI.getUser(id);
    }

    /**
     * @param  username of the user to get
     * @return User data of the username
     */
    @Override
    public User findUserByUsername(String username) {
        return nmAPI.getUser(username);
    }

    @Override
    public boolean isLoggedIn(User user) {
        return false;
    }

    /**
     * @param  user the user that is to be saved
     * @return call to server repo
     */
    @Override
    public Call<User> createAccount(User user) {
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
        return nmAPI.login(username, password);
    }

    @Override
    public Boolean logout() {
        return null;
    }
}

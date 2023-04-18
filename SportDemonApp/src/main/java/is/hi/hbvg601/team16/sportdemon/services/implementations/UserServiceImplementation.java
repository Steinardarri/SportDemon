package is.hi.hbvg601.team16.sportdemon.services.implementations;

import java.util.UUID;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.LoginData;
import is.hi.hbvg601.team16.sportdemon.services.UserService;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import retrofit2.Call;

public class UserServiceImplementation implements UserService {

    private final NetworkManagerAPI nmAPI;

    public UserServiceImplementation(NetworkManagerAPI networkManager){
        this.nmAPI = networkManager;
    }

    /**
     * @param  id of the user to get
     * @return User data of the id
     */
    @Override
    public Call<User> findUserByID(UUID id) {
        return nmAPI.getUser(id);
    }

    /**
     * @param  user the user that is to be saved
     * @return Call to server repo to create account
     */
    @Override
    public Call<User> createAccount(User user) {
        return nmAPI.createAccount(user);
    }

    /**
     * @param  user the user that is to be saved over
     * @return Call to server repo to edit account
     */
    @Override
    public Call<User> editAccount(User user) {
        return nmAPI.createAccount(user); // Notar sama save() í crud og createAccount
    }                                     // sem bæði nýskráir og uppfærir

    @Override
    public String deleteAccount(UUID id) {
        return null;
    }

    /**
     * @param  username of the account to login
     * @param  password of the account to login
     * @return Call to server repo to login
     */
    @Override
    public Call<LoginData> login(String username, String password) {
        return nmAPI.login(username, password);
    }
}

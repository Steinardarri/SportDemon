package is.hi.hbvg601.team16.sportdemon.services;

import java.util.UUID;

import is.hi.hbvg601.team16.sportdemon.persistence.LoginData;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import retrofit2.Call;

public interface UserService {

    /**
     * @param  user that is to be created
     * @return call to server to create account
     */
    Call<User> createAccount(User user);

    /**
     * @param  user the user that is to be saved over
     * @return Call to server repo to edit account
     */
    Call<User> editAccount(User user);

    /**
     * @param  id of the user to delete
     * @return execute result
     */
    String deleteAccount(UUID id); // Not implemented

    /**
     * @param  username of the account to login
     * @param  password of the account to login
     * @return call to server to login
     */
    Call<LoginData> login(String username, String password);

    /**
     * @param  id of the user to get
     * @return call to server
     */
    Call<User> findUserByID(UUID id);

}

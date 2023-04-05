package is.hi.hbvg601.team16.sportdemon.services;

import java.util.UUID;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import retrofit2.Call;

public interface UserService {

    /**
     * @param  user to check if logged in
     * @return boolean success
     */
    boolean isLoggedIn(User user); // Not implemented

    /**
     * @param  user that is to be created
     * @return call to server to create account
     */
    Call<User> createAccount(User user);

    /**
     * @param  user that is to be saved over
     * @return execute result
     */
    String editAccount(User user); // Not implemented

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
    Call<User> login(String username, String password);

    /**
     * @return boolean success
     */
    Boolean logout(); // Not implemented

    /**
     * @param  id of the user to get
     * @return call to server
     */
    Call<User> findUserByID(UUID id);

    /**
     * @param  username of the user to get
     * @return call to server to find user
     */
    Call<User> findUserByUsername(String username);
}
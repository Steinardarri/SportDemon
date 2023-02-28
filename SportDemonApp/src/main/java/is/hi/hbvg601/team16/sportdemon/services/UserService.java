package is.hi.hbvg601.team16.sportdemon.services;

import java.util.UUID;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;

public interface UserService {

    /**
     * @param user to check if logged in
     * @return boolean success
     */
    boolean isLoggedIn(User user);

    /**
     * @param user that is to be created
     * @return execute result
     */
    String createAccount(User user);

    /**
     * @param user that is to be saved over
     * @return execute result
     */
    String editAccount(User user);

    /**
     * @param id of the user to delete
     * @return execute result
     */
    String deleteAccount(UUID id);

    /**
     * @param username of the account to login
     * @param password of the account to login
     * @return execute result
     */
    String login(String username, String password);

    /**
     * @return boolean success
     */
    Boolean logout();

    /**
     * @param id of the user to get
     * @return User data of the id
     */
    User findUserByID(UUID id);
}
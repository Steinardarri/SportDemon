package is.hi.hbvg601.team16.sportdemon.services;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;

import java.util.*;

public interface UserService {

    /**
     * Standard method to save a user.
     *
     * @param user the user that is to be saved
     * @return boolean success
     */
    boolean save(User user);

    /**
     * Standard method to delete a user.
     *
     * @param user the user that is to be deleted
     */
    void delete(User user);

    /**
     * Standard method to find all users.
     *
     * @return a list of all users
     */
    List<User> findAll();

    /**
     * Standard method to create a server account for user
     *
     * @param  user to create an account for
     * @return success response for creation
     */
    String createAccount(User user);

    /**
     * Standard method to login user
     *
     * @param user the user to be logged in
     * @return success response for a username and password match
     */
    User login(User user);

    /**
     * Standard method to find user by username.
     *
     * @param username the username of the user to be found
     * @return the user corresponding to the username if it exists
     */
    User findByUsername(String username);

    /**
     * Standard method to find user by ID.
     *
     * @param id the ID of the user to be found
     * @return the user corresponding to the ID if it exists
     */
    User findByID(long id);

    /**
     * Method that adds a workout to the current user
     *
     * @param workout workout that is added to the user
     * @param user the current user
     */
    void addWorkoutToUser(Workout workout, User user);

//    /**
//     * Boolean method that checks if a user is logged in.
//     *
//     * @param session the current session
//     * @return true if user is logged in, else false
//     */
//    public Boolean userLoggedIn(HttpSession session);
}
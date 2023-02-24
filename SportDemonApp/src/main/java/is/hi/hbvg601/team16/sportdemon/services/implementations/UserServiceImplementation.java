package is.hi.hbvg601.team16.sportdemon.services.implementations;

import is.hi.hbvg601.team16.sportdemon.services.NetworkManager;
import is.hi.hbvg601.team16.sportdemon.services.UserService;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;
import retrofit2.Call;
import retrofit2.Callback;

import java.util.UUID;
import java.util.List;

public abstract class UserServiceImplementation implements UserService {

    private final NetworkManager mNetworkManager;

    public UserServiceImplementation(NetworkManager networkManager){
        this.mNetworkManager = networkManager;
    }

    /**
     * Basic save service.
     *
     * @param user the user that is to be saved
     * @return boolean success
     */
    public boolean save(User user) {
        Call<User> response = mNetworkManager.createAccount(user);
        return response.isExecuted();
    }

//    /**
//     * Basic delete service.
//     *
//     * @param user the user that is to be deleted
//     */
//    public void delete(User user) {
//        mNetworkManager.delete(user);
//    }
//
//
//    /**
//     * Basic find all service.
//     *
//     * @return a list of all the users
//     */
//    public List<User> findAll() {
//        return mNetworkManager.findAll();
//    }
//
//    /**
//     * Basic find one service.
//     *
//     * @param username the username of the user to be found
//     * @return the user corresponding to the specific username if it exists
//     */
//    public User findByUsername(String username) {
//        return mNetworkManager.findByUsername(username);
//    }
//
//    /**
//     * Basic Boolean service that checks if the user is logged in
//     *
//     * @param session HttpSession
//     * @return true if user is logged in,  else false
//     *
//     */
////    public Boolean userLoggedIn(HttpSession session) {
////        if(session.getAttribute("LoggedInUser") != null) {
////            return true;
////        }
////        return false;
////    }
//
//    public void createAccount(User user) {
//
//        // Define our request and enqueue
//        Call<User> call = NetworkManagerAPI.getAPI().createAccount(user);
//
//        // Go ahead and enqueue the request
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onSuccess(Response<Device> deviceResponse) {
//                // Take care of your device here
//                if (deviceResponse.isSuccess()) {
//                    // Handle success
//                    //delegate.passDeviceObject();
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                // Go ahead and handle the error here
//            }
//
//        });
//    }
//
//    /**
//     * Service that checks if user exists and whether the password is correct
//     *
//     * @param user current user
//     * @return doesExist which us the current user if the user exists and
//     * the password is correct. Otherwise, returns null.
//     */
//    public User login(User user) {
//        User doesExist = findByUsername(user.getUsername());
//        if(doesExist != null){
//            if(doesExist.getPassword().equals(user.getPassword())){
//                return doesExist;
//            }
//        }
//        return null;
//    }
//
//    /**
//     * Basic find one service.
//     *
//     * @param id the ID of the user to be found
//     * @return the user corresponding to the ID if it exists
//     */
//    public User findByID(long id){
//        return mNetworkManager.userRepository.findByID(id);
//    }
//
//    /**
//     * Service that adds a workout to the current user.
//     *
//     * @param user the current user
//     * @param workout workout that is added to the current user
//     */
//    public void addWorkoutToUser(Workout workout, User user ) {
//        List <Workout> l = user.getWorkoutList();
//        l.add(workout);
//        user.setWorkoutList(l);
//        this.save(user);
//    }
}

package is.hi.hbvg601.team16.sportdemon.persistence.entities;

import java.util.*;

/******************************************************************************
 *  Name    : Team 16
 *  Members :
 *      Arnar Gauti Ólafsson - ago13@hi.is
 *      Fannar Skúli Birgisson - fsb2@hi.is
 *      Magnea Mist Ólafsdóttir - mmo3@hi.is
 *      Steinar Darri Þorgilsson - sth319@hi.is
 * <p>
 *  Description  : A class that generates the User entity.
 *****************************************************************************/

public class User {

    private UUID ID;
    private String mUsername;
    private String mPassword;
    private String mEmail;
    private List<Workout> mWorkoutList;
    private List<WorkoutResult> mJournal;

    public User() {
    }

    public User(String username, String password, String email) {
        this.mUsername = username;
        this.mPassword = password;
        this.mEmail = email;
    }

    public UUID getID() {
        return ID;
    }

    public String getUsername() {
        return mUsername;
    }
    public void setUsername(String username) {
        this.mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }
    public void setPassword(String password) {
        this.mPassword = password;
    }

    public String getEmail() {
        return mEmail;
    }
    public void setEmail(String string) {
        this.mEmail = string;
    }

    public List<Workout> getWorkoutList() {
        return mWorkoutList;
    }
    public void setWorkoutList(List<Workout> workoutList) {
        this.mWorkoutList = workoutList;
    }

    public List<WorkoutResult> getJournal() {
        return mJournal;
    }
    public void setJournal(List<WorkoutResult> journal) {
        this.mJournal = journal;
    }
}

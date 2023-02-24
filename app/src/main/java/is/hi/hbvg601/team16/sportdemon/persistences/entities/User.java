package is.hi.hbvg601.team16.sportdemon.persistences.entities;

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
    private String username;
    private String password;
    private String email;
    private List<Workout> myWorkouts;
    private List<WorkoutResult> journal;

    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public UUID getID() {
        return ID;
    }
    public void setID(UUID ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String string) {
        this.email = string;
    }

    public List<Workout> getMyWorkouts() {
        return myWorkouts;
    }
    public void setMyWorkouts(List<Workout> myWorkouts) {
        this.myWorkouts = myWorkouts;
    }

    public List<WorkoutResult> getJournal() {
        return journal;
    }
    public void setJournal(List<WorkoutResult> journal) {
        this.journal = journal;
    }
}

package is.hi.hbvg601.team16.sportdemon.persistence.entities;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

public class User implements Serializable {

    private UUID ID;
    private String username;
    private String password;
    private String email;
    private List<Workout> workoutList;
    private List<WorkoutResult> workoutResultList;

    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public UUID getID() {
        return ID;
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

    public List<Workout> getWorkoutList() {
        return workoutList;
    }
    public void setWorkoutList(List<Workout> workoutList) {
        this.workoutList = workoutList;
    }

    public List<WorkoutResult> getWorkoutResultList() {
        return workoutResultList;
    }
    public void setWorkoutResultList(List<WorkoutResult> workoutResultList) {
        this.workoutResultList = workoutResultList;
    }
}

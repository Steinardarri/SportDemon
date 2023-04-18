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

    private UUID id;
    private String username;
    private String password;
    private String email;
    private List<Workout> workoutList = new ArrayList<>();
    private List<WorkoutResult> workoutResultList = new ArrayList<>();

    public User() {}
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "ID=" + getId() +
                ", username='" + getUsername() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
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

    public void addWorkout(Workout w) {
        workoutList.add(w);
    }

    public void editWorkout(Workout newWorkout) {
        for (Workout w : workoutList){
            if (w.getId().equals(newWorkout.getId())){
                int i = workoutList.indexOf(w);
                workoutList.set(i, newWorkout);
                break;
            }
        }
    }

    public void addWorkoutResult(WorkoutResult wr) {
        workoutResultList.add(wr);
    }

}

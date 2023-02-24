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
 *  Description  : A class that generates the Workout entity.
 *****************************************************************************/

public class Workout {

    private UUID ID;
    private User user;
    private String title;
    private int duration;
    private String description;
    private List<ExerciseCombo> exerciseCombo = new ArrayList<>();

    public Workout() {
    }

    public Workout(String title, int duration, String description) {
        this.title = title;
        this.duration = duration;
        this.description = description;
    }

    public UUID getID() {
        return ID;
    }
    public void setID(UUID ID) {
        this.ID = ID;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public List<ExerciseCombo> getExerciseCombo() {
        return exerciseCombo;
    }
    public void setExerciseCombo(List<ExerciseCombo> exerciseCombo) {
        this.exerciseCombo = exerciseCombo;
    }
}





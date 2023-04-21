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
 *  Description  : A class that generates the Workout entity.
 *****************************************************************************/

public class Workout implements Serializable {

    private UUID id;
    private UUID user;
    private String title;
    private String description;
    private int restBetweenEC;
    private int duration;
    private List<ExerciseCombo> exerciseComboList = new ArrayList<>();

    public Workout() {}
    public Workout(String title, String description, int rest) {
        this.title = title;
        this.description = description;
        this.restBetweenEC = rest;
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUser() {
        return user;
    }
    public void setUser(UUID user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getRestBetweenEC() {
        return restBetweenEC;
    }
    public void setRestBetweenEC(int restBetweenEC) {
        this.restBetweenEC = restBetweenEC;
    }

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<ExerciseCombo> getExerciseComboList() {
        return exerciseComboList;
    }
    public void setExerciseComboList(List<ExerciseCombo> exerciseComboList) {
        this.exerciseComboList = exerciseComboList;
    }

    public void addExerciseCombo(ExerciseCombo ec) {
        exerciseComboList.add(ec);
    }

    public void editExerciseCombo(ExerciseCombo newEC) {
        for (ExerciseCombo ec : exerciseComboList){
            if (ec.getId().equals(newEC.getId())){
                int i = exerciseComboList.indexOf(ec);
                exerciseComboList.set(i, newEC);
                break;
            }
        }
    }

    public void removeExerciseCombo(ExerciseCombo ec) {
        for (ExerciseCombo oldEC : exerciseComboList){
            if (oldEC.getId().equals(ec.getId())){
                exerciseComboList.remove(oldEC);
                break;
            }
        }
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder workout = new StringBuilder("Workout Summary:\n");
        for (ExerciseCombo ec : exerciseComboList) {
            workout.append("\n-------\n").append(ec.toString());
        }
        return workout.toString();
    }
}





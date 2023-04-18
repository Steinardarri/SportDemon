package is.hi.hbvg601.team16.sportdemon.persistence.entities;

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
    private int duration;
    private String description;
    private List<ExerciseCombo> exerciseComboList = new ArrayList<>();

    public Workout() {}
    public Workout(String title, String description) {
        this.title = title;
        this.description = description;
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

    public List<ExerciseCombo> getExerciseComboList() {
        return exerciseComboList;
    }
    public void setExerciseComboList(List<ExerciseCombo> exerciseComboList) {
        this.exerciseComboList = exerciseComboList;
    }

    public void addExerciseCombo(ExerciseCombo ec) {
        exerciseComboList.add(ec);
    }
}





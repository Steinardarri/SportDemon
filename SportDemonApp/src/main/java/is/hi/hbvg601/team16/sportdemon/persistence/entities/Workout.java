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
    private UUID user_id;
    private String title;
    private int duration;
    private String description;
    private List<ExerciseCombo> exerciseCombo = new ArrayList<>();

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

    public UUID getUser_id() {
        return user_id;
    }
    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
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

    public void addExerciseCombo(ExerciseCombo ec) {
        exerciseCombo.add(ec);
    }
}





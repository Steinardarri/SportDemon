package is.hi.hbvg601.team16.sportdemon.persistence.entities;

import androidx.annotation.NonNull;

import java.io.Serializable;
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

public class ExerciseCombo implements Serializable {

    private UUID id;
    private UUID workout_id;
    private String title;
    private int sets;
    private int reps;
    private double weight;
    private String equipment;
    private int durationPerSet;
    private int restBetweenSets;

    public ExerciseCombo() {}
    public ExerciseCombo(String title, int sets, int reps, double weight,
                         String equipment, int durationPerSet, int restBetweenSets) {
        this.title = title;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.equipment = equipment;
        this.durationPerSet = durationPerSet;
        this.restBetweenSets = restBetweenSets;
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID ID) {
        this.id = ID;
    }

    public UUID getWorkout_id() {
        return workout_id;
    }
    public void setWorkout_id(UUID workout_id) {
        this.workout_id = workout_id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public int getSets() {
        return sets;
    }
    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }
    public void setReps(int reps) {
        this.reps = reps;
    }

    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getEquipment() {
        return equipment;
    }
    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public int getDurationPerSet() {
        return durationPerSet;
    }
    public void setDurationPerSet(int durationPerSet) {
        this.durationPerSet = durationPerSet;
    }

    public int getRestBetweenSets() {
        return restBetweenSets;
    }
    public void setRestBetweenSets(int restBetweenSets) {
        this.restBetweenSets = restBetweenSets;
    }

    @NonNull
    @Override
    public String toString() {
        String info = "";
        info += ""    + getReps() + " reps" +
                " - " + getSets() + " sets";
        String magn = "";
        if (getWeight() > 0) {
            magn += getWeight() + " kg";
        } else if (getDurationPerSet() > 0) {
            magn += getDurationPerSet() + " sek á set";
        }
        if (!magn.equals("")) {
            info += " - " + magn;
        }
        return info;
    }
}

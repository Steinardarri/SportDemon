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

public class ExerciseCombo {
    private UUID id;
    private int sets;
    private int reps;
    private double weight;
    private String equipment;
    private int durationPerSet;
    private int restBetweenSets;
    private Exercise exercise;

    public ExerciseCombo() {
    }

    public ExerciseCombo(int sets, int reps, double weight, Exercise exercise) {
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }
}

package is.hi.hbvg601.team16.SportDemon.Persistences.Entities;

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


}





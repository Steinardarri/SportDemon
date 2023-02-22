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
    private String string;
    private List<Workout> myWorkouts;
    private List<WorkoutResult> journal;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

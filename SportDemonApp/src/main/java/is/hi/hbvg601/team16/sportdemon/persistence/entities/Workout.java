package is.hi.hbvg601.team16.sportdemon.persistence.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

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

@Entity
public class Workout implements Serializable {

    @PrimaryKey
    @NonNull
    private UUID id;
    private String title;
    private String description;
    private int restBetweenEC;
    private int duration;

    public Workout() {
        this.id = UUID.randomUUID();
    }
    @Ignore
    public Workout(String title, String description, int rest) {
        this.id = UUID.randomUUID();

        this.title = title;
        this.description = description;
        this.restBetweenEC = rest;
    }

    @NonNull
    public UUID getId() {
        return id;
    }
    void setId(@NonNull UUID id) {
        this.id = id;
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

}





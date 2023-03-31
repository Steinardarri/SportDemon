package is.hi.hbvg601.team16.sportdemon.persistence.entities;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/******************************************************************************
 *  Name    : Team 16
 *  Members :
 *      Arnar Gauti Ólafsson - ago13@hi.is
 *      Fannar Skúli Birgisson - fsb2@hi.is
 *      Magnea Mist Ólafsdóttir - mmo3@hi.is
 *      Steinar Darri Þorgilsson - sth319@hi.is
 * <p>
 *  Description  : A class that generates the WorkoutResult entity.
 *****************************************************************************/

public class WorkoutResult implements Serializable {

    private UUID ID;
    private User user;
    private Date date;
    private Workout data;
    private byte[] photo;

    public WorkoutResult() {
    }

    public WorkoutResult(Workout data, byte[] photo) {
        this.data = data;
        this.photo = photo;
    }

    public UUID getID() {
        return ID;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public Workout getData() {
        return data;
    }
    public void setData(Workout data) {
        this.data = data;
    }

    public byte[] getPhoto() {
        return photo;
    }
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}

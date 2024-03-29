package is.hi.hbvg601.team16.sportdemon.persistence.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.sql.Date;
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

    private UUID id;
    private UUID user;
    private Date date;
    private String data;
    private byte[] photo;

    public WorkoutResult() {}
    public WorkoutResult(UUID user, String data, byte[] photo) {
        this.user = user;
        this.data = data;
        this.photo = photo;

        Calendar today = Calendar.getInstance();
        this.date = new java.sql.Date(today.getTimeInMillis());
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

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

    public byte[] getPhoto() {
        return photo;
    }
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}

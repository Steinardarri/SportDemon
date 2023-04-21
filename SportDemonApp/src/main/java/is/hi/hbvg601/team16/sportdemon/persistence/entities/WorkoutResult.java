package is.hi.hbvg601.team16.sportdemon.persistence.entities;

import android.net.Uri;

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

    private UUID id;
    private UUID user;
    private Date date;
    private String data;
    private Uri photoUri;

    public WorkoutResult() {}
    public WorkoutResult(String data, Uri photoUri) {
        this.data = data;
        this.photoUri = photoUri;
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

    public Uri getPhotoUri() {
        return photoUri;
    }
    public void setPhotoUri(Uri photoUri) {
        this.photoUri = photoUri;
    }
}

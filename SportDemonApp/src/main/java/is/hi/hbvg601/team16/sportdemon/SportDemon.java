package is.hi.hbvg601.team16.sportdemon;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;

/**
 * Höndlar SharedPreferences til að vista notuð gögn
 * Til að komast hjá því að sækja frá gagnagrunni í hvert skipti
 */
public class SportDemon extends Application {
    private final SharedPreferences mPrefs;

    public SportDemon(Context context) {
        mPrefs = context.getSharedPreferences("SportDemonData", MODE_PRIVATE);
    }

    /**
     * Sækir vistað workout og breytir frá json í Workout hlut
     * @return Workout sem er verið að vinna í
     */
    public Workout getCurrentWorkout() {
        Gson gson = new Gson();

        String json = mPrefs.getString("CurrentWorkout", "");
        return gson.fromJson(json, Workout.class);
    }
    /**
     * Vistar workout sem json hlut í String
     * @param workout til að vinna á
     */
    public void setCurrentWorkout(Workout workout) {
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();

        String json = gson.toJson(workout);
        prefsEditor.putString("CurrentWorkout", json);
        prefsEditor.apply();
    }

}

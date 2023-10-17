package is.hi.hbvg601.team16.sportdemon.persistence.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;

@Dao
public interface WorkoutDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Workout> insertWorkout(Workout workout);

    @Update
    void updateWorkout(Workout workout);

    @Delete
    Completable deleteWorkout(Workout workout);

    @Query("SELECT * from workout")
    Flowable<List<Workout>> getWorkouts();

}
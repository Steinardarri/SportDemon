package is.hi.hbvg601.team16.sportdemon.persistence.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;
import java.util.UUID;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.transaction.WorkoutWithEC;

@Dao
public interface WorkoutDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertWorkout(Workout workout);

    @Update
    void updateWorkout(Workout workout);

    @Delete
    Completable deleteWorkout(Workout workout);

    @Query("SELECT * from workout")
    Flowable<List<Workout>> getWorkouts();

    @Transaction
    @Query("SELECT * FROM workout WHERE id = :id")
    Single<WorkoutWithEC> getWorkoutWithEC(UUID id);

}

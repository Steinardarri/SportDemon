package is.hi.hbvg601.team16.sportdemon.persistence.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.UUID;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.WorkoutResult;

@Dao
public interface WorkoutResultDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertWorkoutResult(WorkoutResult workoutResult);

    @Update
    Completable updateWorkoutResult(WorkoutResult workoutResult);

    @Delete
    Completable deleteWorkoutResult(WorkoutResult workoutResult);

    @Query("SELECT * from workoutresult WHERE id = :id")
    Single<WorkoutResult> getWorkoutResult(UUID id);

}

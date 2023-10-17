package is.hi.hbvg601.team16.sportdemon.persistence.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.UUID;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;

@Dao
public interface ExerciseComboDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<ExerciseCombo> insertExerciseCombo(ExerciseCombo exerciseCombo);

    @Update
    Completable updateExerciseCombo(ExerciseCombo exerciseCombo);

    @Delete
    Completable deleteExerciseCombo(ExerciseCombo exerciseCombo);

    @Query("SELECT * from exercisecombo WHERE workout = :workout")
    Single<List<ExerciseCombo>> loadExerciseComboByWorkout(UUID workout);

}

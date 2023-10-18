package is.hi.hbvg601.team16.sportdemon.persistence.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import io.reactivex.rxjava3.core.Completable;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;

@Dao
public interface ExerciseComboDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertExerciseCombo(ExerciseCombo exerciseCombo);

    @Update
    Completable updateExerciseCombo(ExerciseCombo exerciseCombo);

    @Delete
    Completable deleteExerciseCombo(ExerciseCombo exerciseCombo);

}

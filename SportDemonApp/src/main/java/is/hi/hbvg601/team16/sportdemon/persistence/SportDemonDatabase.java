package is.hi.hbvg601.team16.sportdemon.persistence;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import is.hi.hbvg601.team16.sportdemon.persistence.dao.ExerciseComboDAO;
import is.hi.hbvg601.team16.sportdemon.persistence.dao.WorkoutDAO;
import is.hi.hbvg601.team16.sportdemon.persistence.dao.WorkoutResultDAO;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.WorkoutResult;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.transaction.Converters;

@Database(
        entities = {
                Workout.class,
                WorkoutResult.class,
                ExerciseCombo.class
        },
        version = 1
)
@TypeConverters({Converters.class})
public abstract class SportDemonDatabase extends RoomDatabase {

    public static final String NAME = "SportDemonDatabase";

    public abstract WorkoutDAO workoutDAO();
    public abstract WorkoutResultDAO workoutResultDAO();
    public abstract ExerciseComboDAO exerciseComboDAO();

}

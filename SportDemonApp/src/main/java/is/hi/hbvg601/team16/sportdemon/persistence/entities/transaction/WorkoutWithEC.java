package is.hi.hbvg601.team16.sportdemon.persistence.entities.transaction;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;

public class WorkoutWithEC {
    @Embedded
    private Workout workout;
    @Relation(
            parentColumn = "id",
            entityColumn = "workoutID"
    )
    private List<ExerciseCombo> exerciseComboList;

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public List<ExerciseCombo> getExerciseComboList() {
        return exerciseComboList;
    }
    public void setExerciseComboList(List<ExerciseCombo> exerciseComboList) {
        this.exerciseComboList = exerciseComboList;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder workoutSB = new StringBuilder("Workout Summary:\n");
        workoutSB.append("\n-------\n");
        for (ExerciseCombo ec : exerciseComboList) {
            workoutSB .append(ec.getTitle())
                    .append("\n")
                    .append(ec)
                    .append("\n-------\n");
        }
        return workoutSB.toString();
    }

}

package is.hi.hbvg601.team16.sportdemon.persistence.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LoginData implements Serializable {

    private User user;

    private List<Workout> workoutList;

    private Map<UUID, List<ExerciseCombo>> exerciseComboMap;

    private List<WorkoutResult> workoutResultList;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Workout> getWorkoutList() {
        return workoutList;
    }

    public void setWorkoutList(List<Workout> workoutList) {
        this.workoutList = workoutList;
    }

    public Map<UUID, List<ExerciseCombo>> getExerciseComboMap() {
        return exerciseComboMap;
    }

    public void setExerciseComboMap(Map<UUID, List<ExerciseCombo>> exerciseComboMap) {
        this.exerciseComboMap = exerciseComboMap;
    }

    public List<WorkoutResult> getWorkoutResultList() {
        return workoutResultList;
    }

    public void setWorkoutResultList(List<WorkoutResult> workoutResultList) {
        this.workoutResultList = workoutResultList;
    }
}

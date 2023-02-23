package is.hi.hbvg601.team16.sportdemon.persistences.entities;

import java.util.*;

public class Exercise {

    private UUID ID;
    private String title;
    private List<String> bodyPart = new ArrayList<>();
    private List<String> type = new ArrayList<>();

    public Exercise() {
    }

    public Exercise(String title, List<String> bodyPart, List<String> type) {
        this.title = title;
        this.bodyPart = bodyPart;
        this.type = type;
    }

    public UUID getID() {
        return ID;
    }
    public void setID(UUID ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getBodyPart() {
        return bodyPart;
    }
    public void setBodyPart(List<String> bodyPart) {
        this.bodyPart = bodyPart;
    }

    public List<String> getType() {
        return type;
    }
    public void setType(List<String> type) {
        this.type = type;
    }
}

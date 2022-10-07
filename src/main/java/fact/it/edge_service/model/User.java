package fact.it.edge_service.model;

public class User {
    private String userID;
    private String name;
    private String email;
    private int avatarID;
    private int score;

    //CONTRUCTORS
    public User() {
    }

    public User(String userID, String name, String email, int avatarID, int score) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.avatarID = avatarID;
        this.score = score;
    }

    //GETTER AND SETTERS
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAvatarID() {
        return avatarID;
    }

    public void setAvatarID(int avatarID) {
        this.avatarID = avatarID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

package fact.it.edge_service.model;

public class User {
    private String id;
    private int userID;
    private String name;
    private String email;
    private int avatarID;
    private int score;

    /* CONSTRUCTORS */
    //empty constructor
    public User() {
    }

    //constructor with all parameters
    public User(int userID, String name, String email, int avatarID, int score) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.avatarID = avatarID;
        this.score = score;
    }

    //Constructor with only userID, name and avatar
    public User(int userID, String name, int avatarID) {
        this.userID = userID;
        this.name = name;
        this.avatarID = avatarID;
        this.email = name+"@test.com";
        this.score = 0;
    }

    //GETTER AND SETTERS
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
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

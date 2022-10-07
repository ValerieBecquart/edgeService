package fact.it.edge_service.model;

public class Question {
    private int gameId;
    private String question;
    private String theme;
    private double x;
    private double y;
    private String correctanswer;
    private String answertwo;
    private String answerthree;

    //CONTRUCTORS
    public Question() {
    }

    public Question(int gameId, String question, String theme, double x, double y, String correctanswer, String answertwo, String answerthree) {
        this.gameId = gameId;
        this.question = question;
        this.theme = theme;
        this.x = x;
        this.y = y;
        this.correctanswer = correctanswer;
        this.answertwo = answertwo;
        this.answerthree = answerthree;
    }

    //GETTER AND SETTERS
    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getCorrectanswer() {
        return correctanswer;
    }

    public void setCorrectanswer(String correctanswer) {
        this.correctanswer = correctanswer;
    }

    public String getAnswertwo() {
        return answertwo;
    }

    public void setAnswertwo(String answertwo) {
        this.answertwo = answertwo;
    }

    public String getAnswerthree() {
        return answerthree;
    }

    public void setAnswerthree(String answerthree) {
        this.answerthree = answerthree;
    }
}

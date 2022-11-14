package fact.it.edge_service.model;

public class Question {
    private int gameId;
    private String question;
    private int level;
    private double x;
    private double y;
    private String correctanswer, answertwo, answerthree;
    private int scoreOffensive, scoreDefensive;
    private String objectName;

    public Question() {
        //create new question
    }

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public int getScoreOffensive() {
        return scoreOffensive;
    }

    public void setScoreOffensive(int scoreOffensive) {
        this.scoreOffensive = scoreOffensive;
    }

    public int getScoreDefensive() {
        return scoreDefensive;
    }

    public void setScoreDefensive(int scoreDefensive) {
        this.scoreDefensive = scoreDefensive;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }
}

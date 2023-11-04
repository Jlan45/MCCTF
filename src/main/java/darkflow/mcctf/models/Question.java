package darkflow.mcctf.models;

public class Question {
    private String Name;
    private String UUID;



    private int Score;
    private String ConfirmCommand;
    private String StartCommand;

    public Question(String name, String UUID, int score, String confirmCommand, String startCommand) {
        Name = name;
        this.UUID = UUID;
        Score = score;
        ConfirmCommand = confirmCommand;
        StartCommand = startCommand;
    }


    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getConfirmCommand() {
        return ConfirmCommand;
    }

    public void setConfirmCommand(String confirmCommand) {
        ConfirmCommand = confirmCommand;
    }

    public String getStartCommand() {
        return StartCommand;
    }

    public void setStartCommand(String startCommand) {
        StartCommand = startCommand;
    }
}

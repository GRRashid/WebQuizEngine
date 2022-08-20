package engine.quiz;

public class Feedback {

    public boolean success;

    public String feedback;

    public static final Feedback WIN = new Feedback(true, "Congratulations, you're right!");
    public static final Feedback LOSE = new Feedback(false,"Wrong answer! Please, try again.");


    public Feedback(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;

    }

}

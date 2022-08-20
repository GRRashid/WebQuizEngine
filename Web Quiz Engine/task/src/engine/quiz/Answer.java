package engine.quiz;

import java.util.ArrayList;
import java.util.List;

public class Answer {
    public List<Integer> answer = new ArrayList<Integer>();

    public Answer(){}

    public Answer(List<Integer> answers) {
        this.answer = answers;
    }

    public List<Integer> getAnswers() {
        return answer;
    }

    public void setAnswers(List<Integer> answers) {
        this.answer = answers;
    }
}

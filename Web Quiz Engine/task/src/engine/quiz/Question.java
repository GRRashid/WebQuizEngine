package engine.quiz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import engine.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Question{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank(message = "The title must not be empty")
    private String title;
    @NotBlank(message = "The text must not be empty")
    private String text;
    @ElementCollection
    @NotNull
    @Size(min = 2, message = "Minimum 2 answers")
    private List<String> options = new ArrayList<>();
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ElementCollection
    private List<Integer> answer;
    @ManyToOne
    @JsonIgnore
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Question() {
    }
    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public List<String> getOptions() {
        return options;
    }


    public List<Integer> getAnswer() {
        return answer;
    }
    public int getId() {
        return id;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

}

package engine.quiz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import engine.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CompletedQuestion {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JsonProperty("id")
    private int questionId;

    private LocalDateTime completedAt;

    @ManyToOne
    @JsonIgnore
    private User user;

    public CompletedQuestion(){
        this.completedAt = LocalDateTime.now();
    }

    public CompletedQuestion(int Id, User user){
        this.completedAt = LocalDateTime.now();
        this.questionId = Id;
        this.user = user;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public User getUser() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

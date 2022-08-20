package engine.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import engine.quiz.Question;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    @Email(regexp = ".+@.+\\..+")
    private String email;
    @NotNull
    @Size(min = 5, message = "Minimum 5")
    private String password;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Question> questionList = new ArrayList<>();

    public User(){
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }




}

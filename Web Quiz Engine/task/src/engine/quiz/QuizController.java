package engine.quiz;

import engine.user.UserDetailsImpl;
import engine.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

@RestController
public class QuizController {

    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    private CompletedQuestionRepository completedQuestions;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/api/quizzes")
    public Question getQuestion(@Valid @RequestBody Question question,
                                @AuthenticationPrincipal UserDetailsImpl details){
        question.setUser(details.getUser());
        questionRepository.save(question);
        return question;
    }

    @GetMapping(path = "/api/quizzes/{id}")
    public Question getQuestion(@PathVariable int id){
        return questionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/api/quizzes")
    public Page<Question> getAllQuestion(@RequestParam(defaultValue = "0") Integer page,
                                         @RequestParam(defaultValue = "10") Integer pageSize){
        return questionRepository.findAll(PageRequest.of(page, pageSize));
    }

    @PostMapping(path = "/api/quizzes/{id}/solve", consumes = "application/json")
    public Feedback getAnswer(@PathVariable int id,
                              @RequestBody Answer answer,
                              @AuthenticationPrincipal UserDetailsImpl details) {
        Optional<List<Integer>> answer1 = Optional.ofNullable(getQuestion(id).getAnswer());
        Optional<List<Integer>> answer2 = Optional.ofNullable(answer.getAnswers());
        if(Arrays.equals(answer1.orElse(List.of()).toArray(), answer2.orElse(List.of()).toArray())){
            completedQuestions.save(new CompletedQuestion(id, details.getUser()));
            return Feedback.WIN;
        }
        return Feedback.LOSE;
    }

    @DeleteMapping(path = "/api/quizzes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuestion(@PathVariable int id,
                               @AuthenticationPrincipal UserDetailsImpl details){
        Question question = getQuestion(id);
        if (question.getUser().getEmail().equals(details.getUser().getEmail())){
            questionRepository.delete(question);
        }else throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    @GetMapping(path = "/api/quizzes/completed")
    public Page<CompletedQuestion> getAllCompletedQuestions(@RequestParam(defaultValue = "0") Integer page,
                                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                                            @AuthenticationPrincipal UserDetailsImpl details){

        List<CompletedQuestion> listCompletedQuestions = completedQuestions.findAll();
        List<CompletedQuestion> res = new ArrayList<>();
        for (CompletedQuestion cq : listCompletedQuestions) {
            if (cq.getUser().getEmail().equals(details.getUsername())){
                res.add(cq);
            }
        }
        res.sort(Comparator.comparing(CompletedQuestion :: getCompletedAt).reversed());
        int start = page * pageSize;
        int end = start + pageSize;
        if (end > res.size()){
            end = res.size();
        }
        res = res.subList(start, end);

        return new PageImpl<>(res, PageRequest.of(page, pageSize), res.size());
    }



}



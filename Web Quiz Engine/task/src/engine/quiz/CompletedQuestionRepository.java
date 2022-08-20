package engine.quiz;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompletedQuestionRepository extends JpaRepository<CompletedQuestion, Integer> {
}

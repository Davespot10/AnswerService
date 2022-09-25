package edu.miu.cs544.AnswerService.repository;

import edu.miu.cs544.AnswerService.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Long> {
    List<Answer> findAllByQuestionId(Long aLong);

    List<Answer> deleteAllByQuestionId(Long id);

    Long deleteByQuestionId(Long id);
}

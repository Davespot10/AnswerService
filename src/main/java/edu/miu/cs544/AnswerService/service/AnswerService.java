package edu.miu.cs544.AnswerService.service;

import edu.miu.cs544.AnswerService.Exception.RecordNotFoundException;
import edu.miu.cs544.AnswerService.dto.AnswerRequest;
import edu.miu.cs544.AnswerService.dto.AnswerResponse;
import edu.miu.cs544.AnswerService.dto.AnswerUpdateDto;
import edu.miu.cs544.AnswerService.model.Answer;
import edu.miu.cs544.AnswerService.repository.AnswerRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {
    @Autowired
    private AnswerRepository answerRepository;

    public void creatAnswer(AnswerRequest answerRequest) {
        Answer answer = new Answer();
        answer.setAnsweredTime(LocalDate.now());
        answer.setSolution(answerRequest.getSolution());
        answer.setUserId(answerRequest.getUserId());
        answer.setQuestionId(answerRequest.getQuestionId());
        answerRepository.save(answer);
    }

    public List<AnswerResponse> getAllAnswers(AnswerResponse answerResponse) {
        List<Answer> answers = new ArrayList<>();
        answers = answerRepository.findAll();
        return answers.stream().map(answer -> mapToAnswerResponse(answer)).toList();
    }

    public AnswerResponse mapToAnswerResponse(Answer answer) {
        AnswerResponse answerResponse = new AnswerResponse();
        answerResponse.setAnsweredTime(answer.getAnsweredTime());
        answerResponse.setId(answer.getId());
        answerResponse.setSolution(answer.getSolution());
        answerResponse.setQuestionId(answer.getQuestionId());
        answerResponse.setUserId(answer.getUserId());
        return answerResponse;

    }

    public void updateAnswer(Long id, AnswerUpdateDto answerUpdateDto) throws RecordNotFoundException {

        Optional<Answer> answer1 = answerRepository.findById(id);
        if (answer1.isPresent()) {
            Answer answer = answer1.get();
            answer.setAnsweredTime(LocalDate.now());
            answer.setSolution(answerUpdateDto.getSolution());
            answerRepository.save(answer);

        } else {
            throw new RecordNotFoundException("Answer Not Found");
        }
    }

    public void deleteAnswer(Long id) throws RecordNotFoundException{
        Optional<Answer> answer = answerRepository.findById(id);
        if (answer.isPresent()) {
            Answer answer1 = answer.get();
            answerRepository.delete(answer1);
        }
        else {
            throw new RecordNotFoundException("Answer Not Found");


        }
    }
}

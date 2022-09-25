package edu.miu.cs544.AnswerService.service;

import edu.miu.cs544.AnswerService.Exception.RecordNotFoundException;
import edu.miu.cs544.AnswerService.dto.*;
import edu.miu.cs544.AnswerService.model.Answer;
import edu.miu.cs544.AnswerService.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    WebClient.Builder webClientBuilder;

    public void creatAnswer(AnswerRequest answerRequest) {
        Answer answer = new Answer();
        answer.setAnsweredTime(LocalDate.now());
        answer.setSolution(answerRequest.getSolution());
        answer.setUserId(answerRequest.getUserId());
        answer.setQuestionId(answerRequest.getQuestionId());
        answerRepository.save(answer);
    }

//    public List<AnswerResponse> getAllAnswers(AnswerResponse answerResponse) {
//        List<Answer> answers = new ArrayList<>();
//        answers = answerRepository.findAll();
//        return answers.stream().map(answer -> mapToAnswerResponse(answer)).toList();
//    }

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
    public List<AnswerFeedbackResponseDTO> getOne(Long id){
        List<Answer> answer = answerRepository.findAllByQuestionId(id);
        List<AnswerFeedbackResponseDTO> allAnswersResponse = new ArrayList<>();
//        AnswerFeedbackResponseDTO
        int i=0;
        if (answer != null && answer.size() > 0) {
            for(Answer answer1:answer){
                FeedBackResponseDto[] feedBackResponseDto= webClientBuilder.build().get()
                    .uri("http://localhost:8084/feedback",uriBuilder -> uriBuilder.path("/{answerId}").build(answer1.getId()))
                    .retrieve()
                    .bodyToMono(FeedBackResponseDto[].class)
                    .block();
                AnswerFeedbackResponseDTO answerFeedbackResponseDTO=new AnswerFeedbackResponseDTO();
                answerFeedbackResponseDTO.setFeedBackResponseDtoList(Arrays.stream(feedBackResponseDto).toList());
                answerFeedbackResponseDTO.setAnswerResponse(mapToAnswerResponse(answer1));
                allAnswersResponse.add(answerFeedbackResponseDTO);
            }
            return allAnswersResponse;
        }
        else{
            return null;
        }

    }
    public void deleteAnswersByQuestionId(Long id){
//        answerRepository.deleteByQuestionId(id);

        List<Answer> answers=answerRepository.findAllByQuestionId(id);
        if(answers.isEmpty() == false) {
            for (Answer answer:answers){
                webClientBuilder.build().delete()
                        .uri("http://localhost:8084/feedback/answer", uriBuilder -> uriBuilder.path("/{id}").build(answer.getId()))
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
            }
            answerRepository.deleteAll(answers);
        }
        else {
            throw new IllegalArgumentException();
        }
    }
//    public void deleteAnswersByQuestionId2(Long id){
//        answerRepository.deleteByQuestionId(id);
//
//
//
//    }
}

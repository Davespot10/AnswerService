package edu.miu.cs544.AnswerService.controller;

import edu.miu.cs544.AnswerService.Exception.RecordNotFoundException;
import edu.miu.cs544.AnswerService.dto.AnswerRequest;
import edu.miu.cs544.AnswerService.dto.AnswerResponse;
import edu.miu.cs544.AnswerService.dto.AnswerUpdateDto;
import edu.miu.cs544.AnswerService.model.Answer;
import edu.miu.cs544.AnswerService.repository.AnswerRepository;
import edu.miu.cs544.AnswerService.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/answer")
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    @PostMapping("/creatanswer")
    public String creatAnswer(@RequestBody AnswerRequest answerRequest) {
        answerService.creatAnswer(answerRequest);
        return "Answer Created Successfully!!!";
    }

    @GetMapping("/getallsolution")
    public List<AnswerResponse> getAllAnswer(AnswerResponse answerResponse) {
        return answerService.getAllAnswers(answerResponse);
    }

    @PutMapping("/updateanswer/{id}")
    public String updateAnswer(@PathVariable Long id, @RequestBody AnswerUpdateDto answerUpdateDto) throws RecordNotFoundException {

        answerService.updateAnswer(id, answerUpdateDto);
        return " Answer Updated Successfully";
    }

    @DeleteMapping("/deleteanswer/{id}")
    public String deleteAnswer(@PathVariable Long id) throws RecordNotFoundException {
        answerService.deleteAnswer(id);
        return "Answer Deleted Successfully";
    }
}


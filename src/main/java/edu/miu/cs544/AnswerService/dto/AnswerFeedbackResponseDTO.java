package edu.miu.cs544.AnswerService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerFeedbackResponseDTO {
    private AnswerResponse answerResponse;
    private List<FeedBackResponseDto> feedBackResponseDtoList;
}

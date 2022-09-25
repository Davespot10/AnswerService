package edu.miu.cs544.AnswerService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedBackResponseDto {
    private Long id;
    private String comment;
    private Long userId;
    private Long answerId;
    private LocalDate createdDate;
}

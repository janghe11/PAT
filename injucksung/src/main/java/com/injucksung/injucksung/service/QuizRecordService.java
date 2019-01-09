package com.injucksung.injucksung.service;

import com.injucksung.injucksung.domain.QuizRecord;
import com.injucksung.injucksung.security.CustomUserDetails;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface QuizRecordService {

    Page<QuizRecord> getQuizRecordList(Long userId, int start);

    QuizRecord addQuizRecordService(Map<Long, Integer> selectedChoices, CustomUserDetails userDetails, int bookContentCount);

    // TODO: 2018-12-18 이게 과연 필요할까?
    int modifyQuizRecordService(QuizRecord quizRecord);

    // TODO: 2018-12-18 이게 과연 필요할까? 
    void deleteQuizRecordService(Long id);
}

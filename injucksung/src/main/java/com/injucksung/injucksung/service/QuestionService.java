package com.injucksung.injucksung.service;

import com.injucksung.injucksung.domain.Question;
import com.injucksung.injucksung.dto.QuestionForm;
import com.injucksung.injucksung.dto.SelectedBookContentForQuizForm;
import org.springframework.data.domain.Page;

import java.util.List;

public interface QuestionService {
    Question addQuestion(QuestionForm questionForm);

    void deleteQuestion(Long questionId);

    Question modifyQuestion(QuestionForm questionForm);

    List<Question> getQuestionList(Long bookContentId);

    List<Question> getQuestionList(SelectedBookContentForQuizForm selectedBookContentForQuizForm);

    Page<Question> getQuestionList(int start);

    Question getQuestionById(Long questionId);
}
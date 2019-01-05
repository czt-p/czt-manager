package com.zjcds.czt.manager.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zjcds.czt.manager.domain.dto.ExamQuestionForm;
import com.zjcds.czt.manager.domain.dto.OptionForm;
import com.zjcds.czt.manager.domain.entity.jpa.ExamQuestion;
import org.apache.commons.lang3.StringUtils;
import springfox.documentation.spring.web.json.Json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luokp on 2019/1/5.
 */
public abstract class ExamQuestionUtils {

    public static ExamQuestionForm.Owner tranExamQuestion(ExamQuestion examQuestion) throws IOException {
        ExamQuestionForm.ChoiceQuestion result = new ExamQuestionForm.ChoiceQuestion();
        result.setId(examQuestion.getId());
        result.setContent(examQuestion.getContent());
        result.setType(examQuestion.getType());
        result.setOrderNumber(examQuestion.getOrderNumber());
        result.setOptions(JsonUtils.toObject(examQuestion.getDetails(), new TypeReference<List<OptionForm.Owner>>() {
        }));
        return result;
    }

    public static ExamQuestionForm.OwnerWithAnswer tranExamQuestionWithAnswer(ExamQuestion examQuestion) throws IOException {
        ExamQuestionForm.ChoiceQuestionWithAnswer result = new ExamQuestionForm.ChoiceQuestionWithAnswer();
        result.setId(examQuestion.getId());
        result.setContent(examQuestion.getContent());
        result.setType(examQuestion.getType());
        result.setOrderNumber(examQuestion.getOrderNumber());
        if(StringUtils.isNotBlank(examQuestion.getDetails())){
            result.setOptions(JsonUtils.toObject(examQuestion.getDetails(), new TypeReference<List<OptionForm.Owner>>() {
            }));
        }
        return result;
    }

    public static List<ExamQuestionForm.OwnerWithAnswer> tranExamQuestionsWithAnswer(List<ExamQuestion> examQuestions) throws IOException {
        List<ExamQuestionForm.OwnerWithAnswer> result = new ArrayList<>(examQuestions.size());
        for (ExamQuestion examQuestion : examQuestions) {
            result.add(tranExamQuestionWithAnswer(examQuestion));
        }
        return result;
    }

}

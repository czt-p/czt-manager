package com.zjcds.czt.manager.controller;

import com.zjcds.czt.manager.domain.dto.HighTechQuestionForm;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.util.Date;

/**
 * created date：2018-12-25
 *
 * @author niezhegang
 */
public class HighTechQuestionServiceControllerTest {

    @Test
    public void testDecode() {
        HighTechQuestionForm.QueryCondition queryCondition = HighTechQuestionServiceController.decodeCondition("eyJxdWVzdGlvbiI6InRpdGxlIiwiYW5zd2VyIjoidGVzdCIsInN0YXJ0RGF0ZSI6MTU0NTcxMzM5MzQ5NywiZW5kRGF0ZSI6MTU0NTcxMzM5MzQ5N30=");
        System.out.println(queryCondition);
    }

    @Test
    public void testEncode() {
        HighTechQuestionForm.QueryCondition queryCondition = new HighTechQuestionForm.QueryCondition();
        queryCondition.setAnswer("test");
        queryCondition.setQuestion("title");
        queryCondition.setStartDate(new Date());
        queryCondition.setEndDate(new Date());
        System.out.println(HighTechQuestionServiceController.encodeCondition(queryCondition));
    }

    @Test
    public void testEncode1() {
        HighTechQuestionForm.QueryCondition queryCondition = new HighTechQuestionForm.QueryCondition();
//        queryCondition.setAnswer("test");
        queryCondition.setQuestion("知识产权");
        queryCondition.setStartDate(DateUtils.addDays(new Date(),-5));
        queryCondition.setEndDate(new Date());
        System.out.println(HighTechQuestionServiceController.encodeCondition(queryCondition));
    }

}

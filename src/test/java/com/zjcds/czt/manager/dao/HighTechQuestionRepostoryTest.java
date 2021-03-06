package com.zjcds.czt.manager.dao;

import com.zjcds.czt.manager.SpringBootTestSupport;
import com.zjcds.czt.manager.dao.es.HighTechQuestionRepostory;
import com.zjcds.czt.manager.domain.entity.es.HighTechQuestion;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * created date：2018-12-24
 * @author niezhegang
 */
public class HighTechQuestionRepostoryTest extends SpringBootTestSupport{
    @Autowired
    private HighTechQuestionRepostory highTechQuestionRepostory;

    @Test
    public void testInsert() {
        Date date = new Date();
        Date modifiedDate = null;
        for (int i = 1 ; i <= 100 ;i++) {
            HighTechQuestion highTechQuestion = new HighTechQuestion();
            highTechQuestion.setQuestion("自主知识产权问题？"+i);
            highTechQuestion.setAnswer("自主知识产权回答"+i);
            highTechQuestion.setAddtime(date);
            modifiedDate = DateUtils.addDays(date,i);
            System.out.println(modifiedDate);
            highTechQuestion.setUpdatedTime(modifiedDate);
            highTechQuestionRepostory.save(highTechQuestion);
        }
    }

    @Test
    public void testSelectUpdate() {
        Iterable<HighTechQuestion> highTechQuestions = highTechQuestionRepostory.findAll();
        for(HighTechQuestion highTechQuestion : highTechQuestions) {
            System.out.println(highTechQuestion);
            highTechQuestion.setUpdatedTime(new Date());
            highTechQuestionRepostory.save(highTechQuestion);
        }
    }


}

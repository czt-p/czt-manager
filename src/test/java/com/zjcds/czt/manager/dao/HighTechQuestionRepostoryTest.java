package com.zjcds.czt.manager.dao;

import com.zjcds.czt.manager.SpringBootTestSupport;
import com.zjcds.czt.manager.dao.es.HighTechQuestionRepostory;
import com.zjcds.czt.manager.domain.entity.es.HighTechQuestion;
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
        HighTechQuestion highTechQuestion = new HighTechQuestion();
        highTechQuestion.setQuestion("自主知识产权问题？");
        highTechQuestion.setAnswer("自主知识产权回答");
        highTechQuestion.setAddtime(new Date());
        highTechQuestion.setUpdatedTime(new Date());
        highTechQuestionRepostory.save(highTechQuestion);
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

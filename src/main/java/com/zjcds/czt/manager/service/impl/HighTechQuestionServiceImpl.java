package com.zjcds.czt.manager.service.impl;

import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.dozer.BeanPropertyCopyUtils;
import com.zjcds.common.jpa.PageResult;
import com.zjcds.czt.manager.dao.es.HighTechQuestionRepostory;
import com.zjcds.czt.manager.domain.dto.HighTechQuestionForm;
import com.zjcds.czt.manager.domain.entity.es.HighTechQuestion;
import com.zjcds.czt.manager.service.HighTechQuestionService;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * created date：2018-12-24
 * @author niezhegang
 */
@Service
public class HighTechQuestionServiceImpl implements HighTechQuestionService{

    @Autowired
    private HighTechQuestionRepostory highTechQuestionRepostory;

    @Override
    public PageResult<HighTechQuestion> queryHighTechQuestion(HighTechQuestionForm.QueryCondition queryCondition, Paging paging) {
//        QueryBuilders.boolQuery().must()
//        highTechQuestionRepostory.search()
        return null;
    }

    @Override
    public HighTechQuestion addHighTechQuestion(HighTechQuestionForm.Add highTechQuestionFormAdd) {
        HighTechQuestion highTechQuestion = BeanPropertyCopyUtils.copy(highTechQuestionFormAdd,HighTechQuestion.class);
        Date currentDate = new Date();
        highTechQuestion.setAddtime(currentDate);
        highTechQuestion.setUpdatedTime(currentDate);
        return highTechQuestionRepostory.save(highTechQuestion);
    }

    @Override
    public HighTechQuestion updateHighTechQuestion(String id, HighTechQuestionForm.Update highTechQuestionFormUpdate) {
        HighTechQuestion highTechQuestion = highTechQuestionRepostory.findOne(id);
        Assert.notNull(highTechQuestion,"未找到id=["+id+"]对应的高新问答！");
        highTechQuestion.setUpdatedTime(new Date());
        highTechQuestion.setQuestion(highTechQuestionFormUpdate.getQuestion());
        highTechQuestion.setAnswer(highTechQuestionFormUpdate.getAnswer());
        return highTechQuestionRepostory.save(highTechQuestion);
    }

    @Override
    public void deleteHighTechQuestion(String id) {
        highTechQuestionRepostory.delete(id);
    }
}

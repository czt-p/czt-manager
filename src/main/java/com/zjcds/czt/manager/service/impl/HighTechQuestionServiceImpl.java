package com.zjcds.czt.manager.service.impl;

import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.dozer.BeanPropertyCopyUtils;
import com.zjcds.common.jpa.PageResult;
import com.zjcds.common.jpa.utils.PageUtils;
import com.zjcds.czt.manager.dao.es.HighTechQuestionRepostory;
import com.zjcds.czt.manager.domain.dto.HighTechQuestionForm;
import com.zjcds.czt.manager.domain.entity.es.HighTechQuestion;
import com.zjcds.czt.manager.service.HighTechQuestionService;
import com.zjcds.czt.manager.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import static org.elasticsearch.index.query.QueryBuilders.*;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
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
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        if(queryCondition != null) {
            BoolQueryBuilder boolQueryBuilder = boolQuery();
            //查询问题标题
            if(StringUtils.isNotBlank(queryCondition.getQuestion())){
                boolQueryBuilder.must(matchQuery("question",queryCondition.getQuestion()));
            }
            //查询问题答案
            if(StringUtils.isNotBlank(queryCondition.getAnswer())){
                boolQueryBuilder.must(matchQuery("answer",queryCondition.getQuestion()));
            }
            //范围过滤
            if(queryCondition.getStartDate() != null && queryCondition.getEndDate() != null) {
                boolQueryBuilder.filter(rangeQuery("updatedTime").format(DateUtils.DefaultPattern).gte(DateUtils.formatDate(queryCondition.getStartDate())).lte(DateUtils.formatDate(queryCondition.getEndDate())));
            }
            nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        }
        //排序和分页
        SearchQuery searchQuery = nativeSearchQueryBuilder
                .withSort(SortBuilders.fieldSort("updatedTime").order(SortOrder.DESC))
                .withPageable(PageUtils.transform(paging))
//                .withHighlightFields(new HighlightBuilder.Field("answer"),new HighlightBuilder.Field("question"))
                .build();
        Page<HighTechQuestion> highTechQuestionPage = highTechQuestionRepostory.search(searchQuery);
        return PageUtils.transformPageResult(highTechQuestionPage,paging);
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

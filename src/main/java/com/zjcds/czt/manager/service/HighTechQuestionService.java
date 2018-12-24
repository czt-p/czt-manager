package com.zjcds.czt.manager.service;

import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.jpa.PageResult;
import com.zjcds.czt.manager.domain.dto.HighTechQuestionForm;
import com.zjcds.czt.manager.domain.entity.es.HighTechQuestion;
import org.springframework.stereotype.Service;


/**
 * created date：2018-12-24
 * @author niezhegang
 */
@Service
public interface HighTechQuestionService {

    /**
     * 分页查询高新问答
     * @param queryCondition
     * @param paging
     * @return
     */
    public PageResult<HighTechQuestion> queryHighTechQuestion(HighTechQuestionForm.QueryCondition queryCondition,Paging paging);

    /**
     * 增加一条高新问题
     * @param highTechQuestionFormAdd
     * @return
     */
    public HighTechQuestion addHighTechQuestion(HighTechQuestionForm.Add highTechQuestionFormAdd);

    /**
     * 修改一条高新问题
     * @param highTechQuestionFormUpdate
     * @return
     */
    public HighTechQuestion updateHighTechQuestion(String id,HighTechQuestionForm.Update highTechQuestionFormUpdate);

    /**
     * 删除一条高新问题
     * @param id
     */
    public void deleteHighTechQuestion(String id);

}

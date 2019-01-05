package com.zjcds.czt.manager.service;

import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.jpa.PageResult;
import com.zjcds.czt.manager.domain.dto.ExamRecordForm;

import java.util.List;

/**
 * @author luokp on 2019/1/5.
 */
public interface ExamRecordService {

    public PageResult<ExamRecordForm.Owner> findExamRecords(Paging paging, List<String> queryString, List<String> orderBy);

    public ExamRecordForm.OwnerWithDetails findExamRecord(Integer id);

}

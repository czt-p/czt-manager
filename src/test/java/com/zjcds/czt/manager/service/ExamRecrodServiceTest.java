package com.zjcds.czt.manager.service;

import com.zjcds.czt.manager.SpringBootTestSupport;
import com.zjcds.czt.manager.domain.dto.ExamRecordForm;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author luokp on 2019/1/5.
 */
public class ExamRecrodServiceTest extends SpringBootTestSupport {

    @Autowired
    private ExamRecordService examRecordService;

    @Test
    public void test() {
        ExamRecordForm.OwnerWithDetails result = examRecordService.findExamRecord(1);
        System.out.println();
    }

}

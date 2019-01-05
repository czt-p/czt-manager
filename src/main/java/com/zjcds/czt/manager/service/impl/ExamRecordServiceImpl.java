package com.zjcds.czt.manager.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Iterators;
import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.jpa.PageResult;
import com.zjcds.common.jpa.utils.PageUtils;
import com.zjcds.czt.manager.dao.ExamRecordDao;
import com.zjcds.czt.manager.dao.jpa.ExamModuleDao;
import com.zjcds.czt.manager.domain.dto.ExamModuleForm;
import com.zjcds.czt.manager.domain.dto.ExamQuestionForm;
import com.zjcds.czt.manager.domain.dto.ExamRecordForm;
import com.zjcds.czt.manager.domain.dto.SubsidyPolicyForm;
import com.zjcds.czt.manager.domain.entity.jpa.ExamModule;
import com.zjcds.czt.manager.domain.entity.jpa.ExamRecord;
import com.zjcds.czt.manager.domain.entity.jpa.SubsidyPolicy;
import com.zjcds.czt.manager.service.ExamRecordService;
import com.zjcds.czt.manager.utils.ExamQuestionUtils;
import com.zjcds.czt.manager.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luokp on 2019/1/5.
 */
@Service
public class ExamRecordServiceImpl implements ExamRecordService {

    @Autowired
    private ExamRecordDao examRecordDao;

    @Autowired
    private ExamModuleDao examModuleDao;

    @Override
    public PageResult<ExamRecordForm.Owner> findExamRecords(Paging paging, List<String> queryString, List<String> orderBy) {
        if (queryString == null) {
            queryString = new ArrayList<>();
        }
        if (orderBy == null) {
            orderBy = new ArrayList<>();
        }
        PageResult<ExamRecord> subsidyPolicyPage = examRecordDao.findAll(paging, queryString, orderBy);
        PageResult<ExamRecordForm.Owner> result = PageUtils.copyPageResult(subsidyPolicyPage, ExamRecordForm.Owner.class);
        return result;
    }

    @Override
    public ExamRecordForm.OwnerWithDetails findExamRecord(Integer id) {
        ExamRecord examRecord = examRecordDao.findOne(id);
        if (examRecord == null) {
            throw new IllegalArgumentException("查询评测记录失败，评测记录[id=" + id + "]不存在");
        }
        ExamRecordForm.OwnerWithDetails result = new ExamRecordForm.OwnerWithDetails();
        result.setId(examRecord.getId());
        result.setCompanyName(examRecord.getCompanyName());
        result.setTelephone(examRecord.getTelephone());
        result.setScore(examRecord.getScore());
        result.setResult(examRecord.getResult());
        result.setExamTime(examRecord.getExamTime());
        try {
            List<ExamQuestionForm.Answer> answers = JsonUtils.toObject(examRecord.getDetails(), new TypeReference<List<ExamQuestionForm.Answer>>() {
            });
            List<ExamModule> allExamModule = examModuleDao.findAllForFetchQuestions();
            List<ExamModuleForm.OwnerWithQuestionAnswer> examModules = tranExamModules(allExamModule, answers);
            result.setDetails(examModules);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<ExamModuleForm.OwnerWithQuestionAnswer> tranExamModules(List<ExamModule> examModules, List<ExamQuestionForm.Answer> answers) throws IOException {
        List<ExamModuleForm.OwnerWithQuestionAnswer> result = new ArrayList<>();
        for (ExamModule examModule : examModules) {
            ExamModuleForm.OwnerWithQuestionAnswer module = new ExamModuleForm.OwnerWithQuestionAnswer();
            module.setId(examModule.getId());
            module.setName(examModule.getName());
            List<ExamQuestionForm.OwnerWithAnswer> questions = ExamQuestionUtils.tranExamQuestionsWithAnswer(examModule.getQuestions());
            for (ExamQuestionForm.OwnerWithAnswer question : questions) {
                ExamQuestionForm.Answer found = Iterators.find(answers.iterator(), answer -> {
                    if (question.getId().equals(answer.getId())) {
                        return true;
                    } else {
                        return false;
                    }
                }, null);
                if (found != null) {
                    question.setAnswer(found.getAnswer());
                }
            }
            module.setQuestions(questions);
            result.add(module);
        }
        return result;
    }
}

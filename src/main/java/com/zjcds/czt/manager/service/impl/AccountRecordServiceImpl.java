package com.zjcds.czt.manager.service.impl;

import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.jpa.PageResult;
import com.zjcds.common.jpa.utils.PageUtils;
import com.zjcds.czt.manager.dao.jpa.AccountRecordDao;
import com.zjcds.czt.manager.domain.dto.AccountRecordForm;
import com.zjcds.czt.manager.domain.dto.account.CostResultDetailForm;
import com.zjcds.czt.manager.domain.entity.jpa.AccountRecord;
import com.zjcds.czt.manager.service.AccountRecordService;
import com.zjcds.czt.manager.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luokp on 2019/1/10.
 */
@Service
public class AccountRecordServiceImpl implements AccountRecordService {

    @Autowired
    private AccountRecordDao accountRecordDao;

    @Override
    public PageResult<AccountRecordForm.Owner> findAccountRecords(Paging paging, List<String> queryString, List<String> orderBy) {
        if (queryString == null) {
            queryString = new ArrayList<>();
        }
        if (orderBy == null) {
            orderBy = new ArrayList<>();
        }
        PageResult<AccountRecord> subsidyPolicyPage = accountRecordDao.findAll(paging, queryString, orderBy);
        PageResult<AccountRecordForm.Owner> result = PageUtils.copyPageResult(subsidyPolicyPage, AccountRecordForm.Owner.class);
        return result;
    }

    @Override
    public AccountRecordForm.OwnerWithDetails findAccountRecord(Integer id) {
        AccountRecord accountRecord = accountRecordDao.findOne(id);
        if (accountRecord == null) {
            throw new IllegalArgumentException("查询评测记录失败，评测记录[id=" + id + "]不存在");
        }
        AccountRecordForm.OwnerWithDetails result = new AccountRecordForm.OwnerWithDetails();
        result.setId(accountRecord.getId());
        result.setCompanyName(accountRecord.getCompanyName());
        result.setTelephone(accountRecord.getTelephone());
        result.setConsultCost(accountRecord.getConsultCost());
        result.setIpCost(accountRecord.getIpCost());
        result.setOtherCost(accountRecord.getOtherCost());
        result.setAnnualAuditCost(accountRecord.getAnnualAuditCost());
        result.setSpecialAuditCost(accountRecord.getSpecialAuditCost());
        result.setTotalCost(accountRecord.getTotalCost());
        result.setAccountTime(accountRecord.getAccountTime());
        try {
            CostResultDetailForm details = JsonUtils.toObject(accountRecord.getDetails(), CostResultDetailForm.class);
            result.setDetails(details);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}

package com.zjcds.czt.manager.service;

import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.jpa.PageResult;
import com.zjcds.czt.manager.domain.dto.AccountRecordForm;

import java.util.List;

/**
 * @author luokp on 2019/1/10.
 */
public interface AccountRecordService {

    public PageResult<AccountRecordForm.Owner> findAccountRecords(Paging paging, List<String> queryString, List<String> orderBy);

    public AccountRecordForm.OwnerWithDetails findAccountRecord(Integer id);

}

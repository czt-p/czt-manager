package com.zjcds.czt.manager.controller;

import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.jpa.PageResult;
import com.zjcds.common.jsonview.annotations.JsonViewException;
import com.zjcds.common.jsonview.utils.ResponseResult;
import com.zjcds.czt.manager.domain.dto.ExamRecordForm;
import com.zjcds.czt.manager.service.ExamRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luokp on 2019/1/5.
 */
@JsonViewException
@RestController
@RequestMapping("/examRecords")
@Api(description = "评测记录管理类操作")
public class ExamRecordManagerController {

    @Autowired
    private ExamRecordService examRecordService;

    @GetMapping
    @ApiOperation(value = "批量查询评测记录操作，支持分页查询", produces = "application/json;charset=utf-8")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageIndex", value = "分页页码", defaultValue = "1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "返回行数", defaultValue = "2147483647", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "queryString", value = "查询条件", defaultValue = "field~Eq~1234", dataType = "String", paramType = "query", allowMultiple = true),
            @ApiImplicitParam(name = "orderBy", value = "排序", defaultValue = "field1Desc", dataType = "String", paramType = "query", allowMultiple = true)})
    public ResponseResult<PageResult<ExamRecordForm.Owner>> querySubsidyPolicies(Paging paging,
                                                                                 @RequestParam(required = false, name = "queryString") List<String> queryString,
                                                                                 @RequestParam(required = false, name = "orderBy") List<String> orderBys) {
        if (CollectionUtils.isEmpty(orderBys)) {
            orderBys = new ArrayList<>();
            orderBys.add("examTimeDesc");
        }
        return new ResponseResult<>(examRecordService.findExamRecords(paging, queryString, orderBys));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询单个评测记录", produces = "application/json;charset=utf-8")
    public ResponseResult<ExamRecordForm.OwnerWithDetails> querySubsidyPolicy(@PathVariable("id") Integer id) {
        return new ResponseResult<>(examRecordService.findExamRecord(id));
    }

}

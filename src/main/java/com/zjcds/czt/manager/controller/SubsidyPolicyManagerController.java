package com.zjcds.czt.manager.controller;

import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.jpa.PageResult;
import com.zjcds.common.jsonview.annotations.JsonFailureBindResult;
import com.zjcds.common.jsonview.annotations.JsonViewException;
import com.zjcds.common.jsonview.utils.ResponseResult;
import com.zjcds.czt.manager.domain.dto.SubsidyPolicyForm;
import com.zjcds.czt.manager.service.SubsidyPolicyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luokp on 2018/12/22.
 */
@JsonViewException
@RestController
@RequestMapping("/subsidyPolicies")
@Api(description = "资助政策管理类操作")
public class SubsidyPolicyManagerController {

    @Autowired
    private SubsidyPolicyService subsidyPolicyService;

    @GetMapping
    @ApiOperation(value = "批量查询资助政策操作，支持分页查询", produces = "application/json;charset=utf-8")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageIndex", value = "分页页码", defaultValue = "1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "返回行数", defaultValue = "2147483647", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "queryString", value = "查询条件", defaultValue = "field~Eq~1234", dataType = "String", paramType = "query", allowMultiple = true),
            @ApiImplicitParam(name = "orderBy", value = "排序", defaultValue = "field1Desc", dataType = "String", paramType = "query", allowMultiple = true)})
    public ResponseResult<PageResult<SubsidyPolicyForm.Owner>> querySubsidyPolicies(Paging paging,
                                                                                    @RequestParam(required = false, name = "queryString") List<String> queryString,
                                                                                    @RequestParam(required = false, name = "orderBy") List<String> orderBys) {
        if (CollectionUtils.isEmpty(orderBys)) {
            orderBys = new ArrayList<>();
            orderBys.add("modifyTimeDesc");
        }
        return new ResponseResult<>(subsidyPolicyService.querySubsidyPolicies(paging, queryString, orderBys));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询单个资助政策", produces = "application/json;charset=utf-8")
    public ResponseResult<SubsidyPolicyForm.Owner> querySubsidyPolicy(@PathVariable("id") Integer id) {
        return new ResponseResult<>(subsidyPolicyService.querySubsidyPolicy(id));
    }

    @PostMapping
    @ApiOperation(value = "添加一个资助政策", produces = "application/json;charset=utf-8")
    @JsonFailureBindResult
    public ResponseResult<SubsidyPolicyForm.Owner> addSubsidyPolicy(@Valid @RequestBody SubsidyPolicyForm.Add subsidyPolicy, BindingResult errorResult) {
        return new ResponseResult<>(subsidyPolicyService.addSubsidyPolicy(subsidyPolicy));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "更新一个资助政策", produces = "application/json;charset=utf-8")
    @JsonFailureBindResult
    public ResponseResult<SubsidyPolicyForm.Owner> updateSubsidyPolicy(@PathVariable("id") Integer id, @Valid @RequestBody SubsidyPolicyForm.Update subsidyPolicy, BindingResult errorResult) {
        return new ResponseResult<>(subsidyPolicyService.updateSubsidyPolicy(id, subsidyPolicy));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除一个资助政策", produces = "application/json;charset=utf-8")
    public ResponseResult<Void> deleteSubsidyPolicy(@PathVariable("id") Integer id) {
        subsidyPolicyService.deleteSubsidyPolicy(id);
        return new ResponseResult<>(null);
    }

}

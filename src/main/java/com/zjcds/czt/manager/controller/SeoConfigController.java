package com.zjcds.czt.manager.controller;

import com.zjcds.common.jsonview.annotations.JsonFailureBindResult;
import com.zjcds.common.jsonview.annotations.JsonViewException;
import com.zjcds.common.jsonview.utils.ResponseResult;
import com.zjcds.czt.manager.domain.dto.SeoConfigForm;
import com.zjcds.czt.manager.service.SeoConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author luokp on 2019/3/12.
 */
@JsonViewException
@RestController
@RequestMapping("/seoConfigs")
@Api(description = "SEO设置类操作")
public class SeoConfigController {

    @Autowired
    private SeoConfigService seoConfigService;

    @GetMapping
    @ApiOperation(value = "查询SEO设置信息", produces = "application/json;charset=utf-8")
    public ResponseResult<List<SeoConfigForm.Owner>> querySeoConfigs() {
        return new ResponseResult<>(seoConfigService.querySeoConfigs());
    }

    @PutMapping
    @ApiOperation(value = "更新SEO设置信息", produces = "application/json;charset=utf-8")
    @JsonFailureBindResult
    public ResponseResult<Void> updateSEOConfig(@Valid @RequestBody SeoConfigForm.UpdateList form, BindingResult errorResult) {
        seoConfigService.updateSeoConfigs(form);
        return new ResponseResult<>(null);
    }

}

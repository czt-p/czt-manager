package com.zjcds.czt.manager.controller;

import com.zjcds.common.jsonview.annotations.JsonFailureBindResult;
import com.zjcds.common.jsonview.annotations.JsonViewException;
import com.zjcds.common.jsonview.utils.ResponseResult;
import com.zjcds.czt.manager.domain.dto.SEOConfigForm;
import com.zjcds.czt.manager.service.SEOConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author luokp on 2019/3/12.
 */
@JsonViewException
@RestController
@RequestMapping("/seoConfig")
@Api(description = "SEO设置类操作")
public class SEOConfigController {

    @Autowired
    private SEOConfigService seoConfigService;

    @GetMapping
    @ApiOperation(value ="查询SEO设置信息",produces = "application/json;charset=utf-8")
    public ResponseResult<SEOConfigForm> getSEOConfig(){
        return new ResponseResult<>(seoConfigService.getSEOConfig());
    }

    @PutMapping
    @ApiOperation(value ="更新SEO设置信息",produces = "application/json;charset=utf-8")
    @JsonFailureBindResult
    public ResponseResult<SEOConfigForm> updateSEOConfig(@Valid @RequestBody SEOConfigForm seoConfigForm, BindingResult errorResult){
        return new ResponseResult<>(seoConfigService.updateSEOConfig(seoConfigForm));
    }

}

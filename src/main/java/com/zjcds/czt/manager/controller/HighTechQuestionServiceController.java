package com.zjcds.czt.manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.dozer.BeanPropertyCopyUtils;
import com.zjcds.common.jpa.PageResult;
import com.zjcds.common.jpa.utils.PageUtils;
import com.zjcds.common.jsonview.annotations.JsonFailureBindResult;
import com.zjcds.common.jsonview.annotations.JsonViewException;
import com.zjcds.common.jsonview.utils.ResponseResult;
import com.zjcds.czt.manager.domain.dto.HighTechQuestionForm;
import com.zjcds.czt.manager.domain.entity.es.HighTechQuestion;
import com.zjcds.czt.manager.service.HighTechQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * created date：2018-12-24
 * @author niezhegang
 */
@JsonViewException
@RestController
@RequestMapping("/highTechQuestions")
@Api(description = "高新技术问题管理类操作")
public class HighTechQuestionServiceController {
    @Autowired
    private HighTechQuestionService highTechQuestionService;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping
    @ApiOperation(value ="批量查询HighTechQuestion操作，支持分页查询",produces = "application/json;charset=utf-8")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageIndex" ,value = "分页页码" ,defaultValue = "1",dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "limit" ,value = "返回行数",defaultValue = "2147483647",dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "queryString" ,value = "使用url安全的base64编码的json格式组装的查询条件",example="{\"question\":\"问题标题\",\"answer\":\"回答答案\",\"startDate\":12131414,\"endDate\":424242422}",dataType = "String",paramType = "query")})
    public ResponseResult<PageResult<HighTechQuestionForm.Owner>> queryHighTechQuestions(Paging paging, @RequestParam(required = false) String queryString) {
        if(paging == null)
            paging = new Paging();
        HighTechQuestionForm.QueryCondition queryCondition = null;
        if(!StringUtils.isBlank(queryString)){
            queryCondition = decodeCondition(queryString);
        }
        PageResult<HighTechQuestion> highTechQuestionPageResult =  highTechQuestionService.queryHighTechQuestion(queryCondition,paging);
        return new ResponseResult(PageUtils.copyPageResult(highTechQuestionPageResult,HighTechQuestionForm.Owner.class));
    }

    @PostMapping
    @ApiOperation(value ="添加一个高新答题",produces = "application/json;charset=utf-8")
    @JsonFailureBindResult
    public ResponseResult<HighTechQuestionForm.Owner> addHighTechQuestion(@Valid @RequestBody HighTechQuestionForm.Add highTechQuestionAdd,BindingResult errorResult){
        HighTechQuestion highTechQuestion = highTechQuestionService.addHighTechQuestion(highTechQuestionAdd);
        return new ResponseResult<>(BeanPropertyCopyUtils.copy(highTechQuestion,HighTechQuestionForm.Owner.class));
    }

    @PutMapping("/{id}")
    @ApiOperation(value ="修改一个高新答题",produces = "application/json;charset=utf-8")
    @JsonFailureBindResult
    public ResponseResult<HighTechQuestionForm.Owner> updateHighTechQuestion(@PathVariable("id") String id,@Valid @RequestBody HighTechQuestionForm.Update highTechQuestionUpdate,BindingResult errorResult) {
        Assert.hasText(id,"id不能为空！");
        HighTechQuestion highTechQuestion = highTechQuestionService.updateHighTechQuestion(id,highTechQuestionUpdate);
        return new ResponseResult<>(BeanPropertyCopyUtils.copy(highTechQuestion,HighTechQuestionForm.Owner.class));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value ="删除一个高新答题",produces = "application/json;charset=utf-8")
    public ResponseResult<Void> deleteHighTechQuestion(@PathVariable("id") String id) {
        Assert.hasText(id,"id不能为空！");
        highTechQuestionService.deleteHighTechQuestion(id);
        return new ResponseResult<>();
    }

    /**
     * 解码查询条件
     * @param src
     * @return
     */
    public static HighTechQuestionForm.QueryCondition decodeCondition(String src) {
        try {
           String condition = new String(Base64.decodeBase64(src.getBytes("UTF-8")),"UTF-8");
            return objectMapper.readValue(condition,HighTechQuestionForm.QueryCondition.class);
        }
       catch (Exception e){
            throw new IllegalArgumentException("解码查询条件出错",e);
       }
    }

    public static String encodeCondition(HighTechQuestionForm.QueryCondition queryCondition) {
        try {
            String condition = objectMapper.writeValueAsString(queryCondition);
            return Base64.encodeBase64URLSafeString(condition.getBytes("UTF-8"));
        }
        catch (Exception e){
            throw new IllegalArgumentException("编码查询条件出错",e);
        }
    }

}

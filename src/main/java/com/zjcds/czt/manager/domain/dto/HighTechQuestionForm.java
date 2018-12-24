package com.zjcds.czt.manager.domain.dto;

import com.zjcds.common.base.domain.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * created date：2018-12-24
 * @author niezhegang
 */
public class HighTechQuestionForm extends BaseBean{

    @Getter
    @Setter
    @ApiModel(value = "HighTechQuestion",description = "高新问题对象")
    public static class Owner {
        @ApiModelProperty(value = "ID",required = true,readOnly = true)
        private String id;
        @ApiModelProperty(value = "问题标题",required = true,readOnly = true)
        private String question;
        @ApiModelProperty(value = "问题回答",required = true,readOnly = true)
        private String answer;
        @ApiModelProperty(value = "问题添加时间",required = true,readOnly = true)
        private Date addtime;
        @ApiModelProperty(value = "问题修改时间",required = true,readOnly = true)
        private Date updatedTime;
    }

    @Getter
    @Setter
    @ApiModel(value = "HighTechQuestionAdd",description = "高新问题对象添加")
    public static class Add {
        @ApiModelProperty(value = "问题标题",required = true)
        @NotNull(message = "问题标题不能为空！")
        private String question;
        @ApiModelProperty(value = "问题回答",required = true)
        @NotNull(message = "问题回答不能为空！")
        private String answer;
    }

    @Getter
    @Setter
    @ApiModel(value = "HighTechQuestionUpdate",description = "高新问题对象修改")
    public static class Update {
        @ApiModelProperty(value = "问题标题",required = true)
        @NotNull(message = "问题标题不能为空！")
        private String question;
        @ApiModelProperty(value = "问题回答",required = true)
        @NotNull(message = "问题回答不能为空！")
        private String answer;
    }

    /**
     * 查询条件
     */
    @Getter
    @Setter
    public static class QueryCondition {
        /**问题标题*/
        private String question;
        /**问题答案*/
        private String answer;
        /**问题修改起始时间*/
        private Date startDate;
        /**问题修改终止时间*/
        private Date endDate;
    }

}

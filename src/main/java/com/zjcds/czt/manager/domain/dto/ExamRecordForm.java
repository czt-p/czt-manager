package com.zjcds.czt.manager.domain.dto;

import com.zjcds.common.base.domain.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author luokp on 2019/1/5.
 */
public class ExamRecordForm {

    @Getter
    @Setter
    @ApiModel(value = "ExamRecord", description = "评测记录信息")
    public static class Owner extends BaseBean {
        @ApiModelProperty(value = "id", required = true, readOnly = true)
        private Integer id;
        @ApiModelProperty(value = "公司名称", required = true, readOnly = true)
        private String companyName;
        @ApiModelProperty(value = "联系电话", required = true, readOnly = true)
        private String telephone;
        @ApiModelProperty(value = "分数", required = true, readOnly = true)
        private Double score;
        @ApiModelProperty(value = "结果", required = true, readOnly = true)
        private String result;
        @ApiModelProperty(value = "评测时间", required = true, readOnly = true)
        private Date examTime;
    }

    @Getter
    @Setter
    @ApiModel(value = "ExamRecordWithDetails", description = "带明细的评测记录信息")
    public static class OwnerWithDetails extends Owner {
        @ApiModelProperty(value = "评测明细", required = true, readOnly = true)
        private List<ExamModuleForm.OwnerWithQuestionAnswer> details;
    }

}

package com.zjcds.czt.manager.domain.dto;

import com.zjcds.common.base.domain.BaseBean;
import com.zjcds.czt.manager.domain.dto.account.CostResultDetailForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author luokp on 2019/1/10.
 */
public class AccountRecordForm {

    @Getter
    @Setter
    @ApiModel(value = "AccountRecord", description = "核算记录信息")
    public static class Owner extends BaseBean {
        @ApiModelProperty(value = "id", required = true, readOnly = true)
        private Integer id;
        @ApiModelProperty(value = "公司名称", required = true, readOnly = true)
        private String companyName;
        @ApiModelProperty(value = "联系电话", required = true, readOnly = true)
        private String telephone;
        @ApiModelProperty(value = "咨询费用，单位：元", required = true, readOnly = true)
        private Double consultCost;
        @ApiModelProperty(value = "知识产权费用，单位：元", required = true, readOnly = true)
        private Double ipCost;
        @ApiModelProperty(value = "其他费用，单位：元", required = true, readOnly = true)
        private Double otherCost;
        @ApiModelProperty(value = "年度审计报告费用，单位：元", required = true, readOnly = true)
        private Double annualAuditCost;
        @ApiModelProperty(value = "专项审计费用，单位：元", required = true, readOnly = true)
        private Double specialAuditCost;
        @ApiModelProperty(value = "总费用，单位：元", required = true, readOnly = true)
        private Double totalCost;
        @ApiModelProperty(value = "核算时间", required = true, readOnly = true)
        private Date accountTime;
    }

    @Getter
    @Setter
    @ApiModel(value = "AccountRecordWithDetails", description = "带明细的核算记录信息")
    public static class OwnerWithDetails extends Owner {
        @ApiModelProperty(value = "明细", required = true, readOnly = true)
        private CostResultDetailForm details;
    }

}

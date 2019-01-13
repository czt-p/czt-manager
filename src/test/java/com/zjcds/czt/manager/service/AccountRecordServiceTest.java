package com.zjcds.czt.manager.service;

import com.zjcds.czt.manager.SpringBootTestSupport;
import com.zjcds.czt.manager.domain.dto.account.CostResultDetailForm;
import com.zjcds.czt.manager.utils.JsonUtils;
import org.junit.Test;

/**
 * @author luokp on 2019/1/10.
 */
public class AccountRecordServiceTest extends SpringBootTestSupport {

    @Test
    public void test() throws Exception {
        String json = "{\"consultCost\":{\"highFieldCost\":{\"highField\":\"电子信息\",\"cost\":10000},\"financialGrowthCost\":{\"financialGrowth\":\"优秀\",\"cost\":10000},\"recentYearIncomingCost\":{\"recentYearIncoming\":\"500万元-2000万元\",\"cost\":10000},\"totalCost\":30000},\"ipCost\":{\"costList\":[{\"type\":\"发明专利\",\"quantity\":2,\"cost\":10000},{\"type\":\"实用性专利\",\"quantity\":2,\"cost\":10000}],\"totalCost\":20000},\"otherCost\":{\"costList\":[{\"type\":\"软件测评报告\",\"quantity\":2,\"cost\":10000},{\"type\":\"查新报告\",\"quantity\":2,\"cost\":10000}],\"totalCost\":20000},\"annualAuditCost\":{\"applyYear\":2018,\"costList\":[{\"year\":\"2017\",\"amount\":500,\"cost\":10000},{\"year\":\"2016\",\"amount\":500,\"cost\":10000},{\"year\":\"2015\",\"amount\":500,\"cost\":10000}],\"totalCost\":30000},\"specialAuditCost\":{\"applyYear\":2018,\"costList\":[{\"year\":2017,\"incoming\":200000,\"managerCost\":300,\"rdCost\":0,\"cost\":10000},{\"year\":2016,\"incoming\":200000,\"managerCost\":0,\"rdCost\":300,\"cost\":10000},{\"year\":2015,\"incoming\":200000,\"managerCost\":300,\"rdCost\":0,\"cost\":10000}],\"totalCost\":30000}}";
        CostResultDetailForm details = JsonUtils.toObject(json, CostResultDetailForm.class);
        System.out.println();
    }

}

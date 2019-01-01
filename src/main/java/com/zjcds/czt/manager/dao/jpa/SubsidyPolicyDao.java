package com.zjcds.czt.manager.dao.jpa;

import com.zjcds.common.jpa.CustomRepostory;
import com.zjcds.czt.manager.domain.entity.jpa.SubsidyPolicy;

/**
 * @author luokp on 2018/12/22.
 */
public interface SubsidyPolicyDao extends CustomRepostory<SubsidyPolicy, Integer> {

    public SubsidyPolicy findByRegionCode(String regionCode);

}

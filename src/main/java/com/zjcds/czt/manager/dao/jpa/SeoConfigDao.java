package com.zjcds.czt.manager.dao.jpa;

import com.zjcds.common.jpa.CustomRepostory;
import com.zjcds.czt.manager.domain.entity.jpa.SeoConfig;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author luokp on 2019/3/14.
 */
public interface SeoConfigDao extends CustomRepostory<SeoConfig, String> {

}

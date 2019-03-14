package com.zjcds.czt.manager.dao;

import com.zjcds.czt.manager.DaoTestSupport;
import com.zjcds.czt.manager.dao.jpa.FrontendPageDao;
import com.zjcds.czt.manager.domain.entity.jpa.FrontendPage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author luokp on 2019/3/14.
 */
public class FrontendPageDaoTest extends DaoTestSupport {

    @Autowired
    private FrontendPageDao frontendPageDao;

    @Test
    public void testFindAllForFetchSeoConfig() {
        List<FrontendPage> result = frontendPageDao.findAllForFetchSeoConfig();
        System.out.println(result);
    }

}

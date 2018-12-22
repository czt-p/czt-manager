package com.zjcds.czt.manager.service.impl;

import com.zjcds.common.dozer.BeanPropertyCopyUtils;
import com.zjcds.czt.manager.dao.jpa.RegionDao;
import com.zjcds.czt.manager.domain.dto.RegionForm;
import com.zjcds.czt.manager.domain.entity.jpa.Region;
import com.zjcds.czt.manager.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author luokp on 2018/12/22.
 */
@Service
@Transactional(readOnly = true)
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionDao regionDao;

    @Override
    public List<RegionForm.Owner> queryAllRegions() {
        return BeanPropertyCopyUtils.copy(regionDao.findAll(), RegionForm.Owner.class);
    }

}

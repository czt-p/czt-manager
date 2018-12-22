package com.zjcds.czt.manager.service.impl;

import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.dozer.BeanPropertyCopyUtils;
import com.zjcds.common.jpa.PageResult;
import com.zjcds.common.jpa.utils.PageUtils;
import com.zjcds.czt.manager.dao.jpa.RegionDao;
import com.zjcds.czt.manager.dao.jpa.SubsidyPolicyDao;
import com.zjcds.czt.manager.domain.dto.SubsidyPolicyForm;
import com.zjcds.czt.manager.domain.entity.jpa.Region;
import com.zjcds.czt.manager.domain.entity.jpa.SubsidyPolicy;
import com.zjcds.czt.manager.service.RegionService;
import com.zjcds.czt.manager.service.SubsidyPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luokp on 2018/12/22.
 */
@Service
@Transactional(readOnly = true)
public class SubsidyPolicyServiceImpl implements SubsidyPolicyService {

    @Autowired
    private SubsidyPolicyDao subsidyPolicyDao;

    @Autowired
    private RegionDao regionDao;

    @Override
    public PageResult<SubsidyPolicyForm.Owner> querySubsidyPolicies(Paging paging, List<String> queryString, List<String> orderBy) {
        if (queryString == null) {
            queryString = new ArrayList<>();
        }
        if (orderBy == null) {
            orderBy = new ArrayList<>();
        }
        PageResult<SubsidyPolicy> subsidyPolicyPage = subsidyPolicyDao.findAll(paging, queryString, orderBy);
        PageResult<SubsidyPolicyForm.Owner> result = PageUtils.copyPageResult(subsidyPolicyPage, SubsidyPolicyForm.Owner.class);
        for (SubsidyPolicyForm.Owner subsidyPolicy : result.getContent()) {
            fillRegionFullName(subsidyPolicy);
        }
        return result;
    }

    @Override
    public SubsidyPolicyForm.Owner querySubsidyPolicy(Integer id) {
        SubsidyPolicyForm.Owner subsidyPolicy = BeanPropertyCopyUtils.copy(subsidyPolicyDao.findOne(id), SubsidyPolicyForm.Owner.class);
        if (subsidyPolicy != null) {
            fillRegionFullName(subsidyPolicy);
        }
        return subsidyPolicy;
    }

    @Transactional
    @Override
    public SubsidyPolicyForm.Owner addSubsidyPolicy(SubsidyPolicyForm.Add subsidyPolicy) {
        Assert.notNull(subsidyPolicy, "要添加的资助政策信息不能为空！");
        SubsidyPolicy subsidyPolicyEntity = BeanPropertyCopyUtils.copy(subsidyPolicy, SubsidyPolicy.class);
        Region region = regionDao.findOne(subsidyPolicy.getRegion());
        if (region == null) {
            throw new IllegalArgumentException("添加资助政策失败，政策所属地区[code=" + subsidyPolicy.getRegion() + "]不存在");
        }
        subsidyPolicyEntity = subsidyPolicyDao.save(subsidyPolicyEntity);
        // TODO: 2018/12/22 log
        SubsidyPolicyForm.Owner result = BeanPropertyCopyUtils.copy(subsidyPolicyEntity, SubsidyPolicyForm.Owner.class);
        fillRegionFullName(result);
        return result;
    }

    @Transactional
    @Override
    public SubsidyPolicyForm.Owner updateSubsidyPolicy(Integer id, SubsidyPolicyForm.Update subsidyPolicy) {
        Assert.notNull(id, "要修改信息的资助政策id不能为空！");
        SubsidyPolicy subsidyPolicyEntity = subsidyPolicyDao.findOne(id);
        if (subsidyPolicyEntity == null) {
            throw new IllegalStateException("修改资助政策失败，该资助政策[id=" + id + "]不存在或已被删除");
        }
        Region region = regionDao.findOne(subsidyPolicy.getRegion());
        if (region == null) {
            throw new IllegalArgumentException("修改资助政策失败，政策所属地区[code=" + subsidyPolicy.getRegion() + "]不存在");
        }
        subsidyPolicyEntity.setName(subsidyPolicy.getName());
        subsidyPolicyEntity.setRegion(subsidyPolicy.getRegion());
        subsidyPolicyEntity.setRegisterDepartment(subsidyPolicy.getRegisterDepartment());
        subsidyPolicyEntity.setContent(subsidyPolicy.getContent());
        subsidyPolicyEntity = subsidyPolicyDao.save(subsidyPolicyEntity);
        // TODO: 2018/12/22 log
        SubsidyPolicyForm.Owner result = BeanPropertyCopyUtils.copy(subsidyPolicyEntity, SubsidyPolicyForm.Owner.class);
        fillRegionFullName(result);
        return result;
    }

    @Override
    public void deleteSubsidyPolicy(Integer id) {
        SubsidyPolicy subsidyPolicy = subsidyPolicyDao.findOne(id);
        if (subsidyPolicy == null) {
            throw new IllegalStateException("删除资助政策失败：资助政策[id=" + id + "]不存在或已被删除，请刷新页面重试！");
        }
        subsidyPolicyDao.delete(id);
        // TODO: 2018/12/22 log
    }

    private void fillRegionFullName(SubsidyPolicyForm.Owner subsidyPolicy) {
        String regionFullName = queryRegionFullName(subsidyPolicy.getRegion());
        subsidyPolicy.setRegionFullName(regionFullName);
    }

    private String queryRegionFullName(String code) {
        StringBuilder sb = new StringBuilder();
        Region region = regionDao.findOne(code);
        if (region == null) {
            return null;
//            throw new IllegalArgumentException("行政地区[code=" + code + "不存在");
        }
        List<Region> regionList = new ArrayList<>();
        regionList.add(region);
        regionList = addParent(region, regionList);
        for (int i = regionList.size() - 1; i >= 0; i--) {
            sb.append(regionList.get(i).getName());
        }
        return sb.toString();
    }

    private List<Region> addParent(Region region, List<Region> regionList) {
        if (StringUtils.hasText(region.getParent())) {
            Region parent = regionDao.findOne(region.getParent());
            if (parent != null) {
                regionList.add(parent);
                regionList = addParent(parent, regionList);
            }
        }
        return regionList;
    }
}

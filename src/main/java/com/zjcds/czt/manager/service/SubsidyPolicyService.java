package com.zjcds.czt.manager.service;

import com.zjcds.common.base.domain.page.Paging;
import com.zjcds.common.jpa.PageResult;
import com.zjcds.czt.manager.domain.dto.SubsidyPolicyForm;

import java.util.List;

/**
 * @author luokp on 2018/12/22.
 */
public interface SubsidyPolicyService {

    /**
     * 分页查询资助政策
     *
     * @param paging      分页信息
     * @param queryString 查询条件
     * @param orderBy     排序规则
     * @return 查询到的资助政策信息
     */
    public PageResult<SubsidyPolicyForm.Owner> querySubsidyPolicies(Paging paging, List<String> queryString, List<String> orderBy);

    /**
     * 根据id查询资助政策
     *
     * @param id 资助政策id
     * @return 查询到的资助政策信息
     */
    public SubsidyPolicyForm.Owner querySubsidyPolicy(Integer id);

    /**
     * 增加资助政策
     *
     * @param subsidyPolicy 待增加的资助政策信息
     * @return 增加后的资助政策信息
     */
    public SubsidyPolicyForm.Owner addSubsidyPolicy(SubsidyPolicyForm.Add subsidyPolicy);

    /**
     * 修改资助政策
     *
     * @param id            待修改的资助政策id
     * @param subsidyPolicy 待修改的资助政策信息
     * @return 修改后的资助政策信息
     */
    public SubsidyPolicyForm.Owner updateSubsidyPolicy(Integer id, SubsidyPolicyForm.Update subsidyPolicy);

    /**
     * 根据id删除资助政策
     *
     * @param id 待删除的资助政策id
     */
    public void deleteSubsidyPolicy(Integer id);

}

package com.zjcds.czt.manager.service;

import com.zjcds.czt.manager.domain.dto.SeoConfigForm;
import com.zjcds.czt.manager.domain.entity.jpa.SeoConfig;

import java.util.List;

/**
 * @author luokp on 2019/3/12.
 */
public interface SeoConfigService {

    public List<SeoConfigForm.Owner> querySeoConfigs();

    public void updateSeoConfigs(SeoConfigForm.UpdateList form);

}

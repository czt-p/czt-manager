package com.zjcds.czt.manager.service;

import com.zjcds.czt.manager.domain.dto.SEOConfigForm;

/**
 * @author luokp on 2019/3/12.
 */
public interface SEOConfigService {

    public SEOConfigForm getSEOConfig();

    public SEOConfigForm updateSEOConfig(SEOConfigForm form);

}

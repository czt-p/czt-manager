package com.zjcds.czt.manager.conf;

import com.zjcds.common.jpa.impl.CustomRepostoryImpl;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;

/**
 * @author luokp on 2018/12/22.
 */
@Configuration
public class CustomRepositoryConfiguration implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 配置ConversionService
        CustomRepostoryImpl.conversionService = applicationContext.getBean(ConversionService.class);
    }
}

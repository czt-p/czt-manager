package com.zjcds.czt.manager.utils;

import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * created date：2018-12-25
 * @author niezhegang
 */
public abstract class DateUtils {

    public static String DefaultPattern = "yyyy-MM-dd HH:mm:ss";

    public static String formatDate(Date date,String pattern){
        Assert.notNull(date,"传入的日期参数不能为空！");
        Assert.hasText(pattern,"模式参数不能为空！");
        return FastDateFormat.getInstance(pattern).format(date);
    }

    public static String formatDate(Date date) {
        return formatDate(date,DefaultPattern);
    }
}

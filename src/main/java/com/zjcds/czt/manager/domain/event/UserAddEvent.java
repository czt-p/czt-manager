package com.zjcds.czt.manager.domain.event;


import com.zjcds.common.syslog.domain.SysLogApplicationEvent;

/**
 * created date：2017-08-15
 * @author niezhegang
 */
public class UserAddEvent extends SysLogApplicationEvent{

    public UserAddEvent(Object source) {
        super(source);
    }

}

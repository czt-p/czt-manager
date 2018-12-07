package com.zjcds.czt.manager.conf;

import com.zjcds.czt.manager.annotation.UserInjectToController;
import com.zjcds.czt.manager.utils.WebSecurityUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * created date：2017-09-15
 * @author niezhegang
 */
@Configuration
public class ControllerAdviceConfiguration {

    @ControllerAdvice(annotations = {UserInjectToController.class})
    public static class AddUserAdvice {

        @ModelAttribute
        public void addUser(Model model){
            model.addAttribute("user", WebSecurityUtils.currentUser());
        }
    }

}

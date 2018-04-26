package pro.xway.registration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pro.xway.registration.Constant;
import pro.xway.registration.service.MyUserService;

@RequestMapping(value = "/user")
@Controller
public class UserController implements Constant {
    @Autowired
    MyUserService userService;

    @RequestMapping
    public String adminPanel(Model model) {
        model.addAttribute(USER, userService.getCurrentUser());
        return USER;
    }

}

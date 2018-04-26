package pro.xway.registration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pro.xway.registration.Constant;
import pro.xway.registration.service.MyUserService;

@RequestMapping(value = "/")
@Controller
public class MainController implements Constant {
    @Autowired
    private MyUserService userService;

    @RequestMapping
    public String index(Model model){
        model.addAttribute(USER, userService.getCurrentUser());
        return  INDEX;
    }
}

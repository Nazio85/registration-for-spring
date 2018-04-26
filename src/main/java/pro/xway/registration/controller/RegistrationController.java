package pro.xway.registration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pro.xway.registration.Config;
import pro.xway.registration.Constant;
import pro.xway.registration.dao.RoleRepository;
import pro.xway.registration.dao.UserRepository;
import pro.xway.registration.model.MyUser;
import pro.xway.registration.service.MailService;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping(value = "/")
public class RegistrationController implements Constant {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MailService mailService;

    @Autowired
    public RegistrationController(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                                  MailService mailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mailService = mailService;
    }

    @RequestMapping(value = LOGIN_PATH, method = RequestMethod.GET)
    public String loginForm() {
        return LOGIN;
    }

    @RequestMapping(value = LOGIN_PATH + REGISTRATION_PATH, method = RequestMethod.POST)
    public String sendEmail(@RequestParam String email) {
        String password = UUID.randomUUID().toString().substring(0, 8);
        AtomicReference<MyUser> myUser = new AtomicReference<>();
        roleRepository.findByRole(Config.USER_CAPS).ifPresent(role -> myUser.set(new MyUser(role, email,
                bCryptPasswordEncoder.encode(password))));
        userRepository.save(myUser.get());
        new Thread(() -> mailService.sendConfirmation(myUser.get().getUsername(), "Welcome - your password", password)).start();
        return REDIRECT + LOGIN_PATH;
    }

    @RequestMapping(value = LOGIN_PATH + REGISTRATION_PATH, method = RequestMethod.GET)
    public String registrationForm() {
        return REGISTRATION;
    }


}

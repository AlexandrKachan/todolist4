package by.lex.todolist.controller;

import by.lex.todolist.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import by.lex.todolist.model.UserModel;

import javax.validation.Valid;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new UserModel());
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute("user") @Valid UserModel userModel,
                                  BindingResult result) {
        logger.info("New user {}", userModel);

        if (result.hasErrors()) {
            return "register";
        }
        if (!userModel.getPassword().equals(userModel.getMatchingPassword())) {
            result.rejectValue("password", "", "Password not matching");
            return "register";
        }

        userService.create(userModel);
        return "redirect:/login";
    }
}

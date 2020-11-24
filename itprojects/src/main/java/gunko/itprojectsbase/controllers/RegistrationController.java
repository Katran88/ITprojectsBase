package gunko.itprojectsbase.controllers;

import gunko.itprojectsbase.database.Role;
import gunko.itprojectsbase.database.User;
import gunko.itprojectsbase.database.repositories.UserRepository;
import gunko.itprojectsbase.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registration")
public class RegistrationController
{
    Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    public String getRegistrationPageController()
    {
        return "registrationPage";
    }

    @PostMapping
    public String postRegistrationPageController(User user, @RequestParam String repeatedPassword, Model model)
    {
        if (user.getPassword().compareTo(repeatedPassword) != 0)
        {
            model.addAttribute("differentPasswords", true);
            logger.info("different passwords");
            return "/registrationPage";
        }

        if (!userService.addUser(user))
        {
            model.addAttribute("userAlreadyExist", true);
            logger.info("user already exist");
            return "/registrationPage";
        }

        logger.info("successful registration");
        model.addAttribute("successfulRegistration", true);
        return "loginPage";
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code, Model model)
    {
        if(userService.activateUser(code))
        {
            logger.info("user activated");
            model.addAttribute("userActivated", true);
        }
        else
        {
            logger.info("user not activated");
            model.addAttribute("userNotActivated", true);
        }

        return "loginPage";
    }
}

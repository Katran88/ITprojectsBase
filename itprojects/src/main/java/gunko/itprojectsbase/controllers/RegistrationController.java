package gunko.itprojectsbase.controllers;

import gunko.itprojectsbase.database.Role;
import gunko.itprojectsbase.database.User;
import gunko.itprojectsbase.database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/registration")
public class RegistrationController
{
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String getRegistrationPageController()
    {
        return "registrationPage";
    }

    @PostMapping
    public String postRegistrationPageController(User user, @RequestParam String repeatedPassword, Model model)
    {
        if (userRepository.findByUsername(user.getUsername()) != null)
        {
            model.addAttribute("userAlreadyExist", true);
            return "/registrationPage";
        }

        if (user.getPassword().compareTo(repeatedPassword) != 0)
        {
            model.addAttribute("differentPasswords", true);
            return "/registrationPage";
        }

        user.setActivated(true);
        user.setRole(Role.User);
        user.setUserCV(" ");
        userRepository.save(user);
        model.addAttribute("successfulRegistration", true);

        return "redirect:/login";
    }
}

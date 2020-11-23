package gunko.itprojectsbase.controllers;

import gunko.itprojectsbase.database.Role;
import gunko.itprojectsbase.database.User;
import gunko.itprojectsbase.database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UsersController
{
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String userList(@AuthenticationPrincipal User currentUser, Model model)
    {
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("users", userRepository.findAll());
        return "usersList";
    }

    @PutMapping("/{username}/edit")
    public String userEditForm(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model)
    {
        if(currentUser.getRole() == Role.Admin)
        {
            switch (user.getRole())
            {
                case Admin:
                    user.setRole(Role.User);
                    break;
                case User:
                    user.setRole(Role.Admin);
                    break;
            }
            userRepository.save(user);
        }

        model.addAttribute("users", userRepository.findAll());
        return "usersList";
    }
}

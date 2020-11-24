package gunko.itprojectsbase.controllers;

import gunko.itprojectsbase.database.Role;
import gunko.itprojectsbase.database.User;
import gunko.itprojectsbase.database.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasAuthority('Admin')")
public class UsersController
{
    Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String userList(@AuthenticationPrincipal User currentUser, Model model)
    {
        logger.info("get request to the users list, admin tab");
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("users", userRepository.findAll());
        return "usersList";
    }

    @PostMapping("/{username}/edit")
    public String userEditForm(
            @AuthenticationPrincipal User currentUser,
            @PathVariable String username,
            Model model)
    {
        User selectedUser = userRepository.findByUsername(username);
        if(currentUser.getRole() == Role.Admin)
        {
            switch (selectedUser.getRole())
            {
                case Admin:
                    selectedUser.setRole(Role.User);
                    logger.info("admin have changed role of some account to the User");
                    break;
                case User:
                    selectedUser.setRole(Role.Admin);
                    logger.info("admin have changed role of some account to the Admin");
                    break;
            }
            userRepository.save(selectedUser);
        }

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("users", userRepository.findAll());
        return "usersList";
    }
}

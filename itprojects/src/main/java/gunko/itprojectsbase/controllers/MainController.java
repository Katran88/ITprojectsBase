package gunko.itprojectsbase.controllers;

import gunko.itprojectsbase.database.Project;
import gunko.itprojectsbase.database.Role;
import gunko.itprojectsbase.database.User;
import gunko.itprojectsbase.database.repositories.ProjectRepository;
import gunko.itprojectsbase.database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MainController
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/")
    public String getWelcomePageController()
    {
        return "welcomePage";
    }

    @GetMapping("/login")
    public String getLoginPageController(Model model)
    {
        return "loginPage";
    }

    @GetMapping("/projects")
    public String getMainPageController(Model model)
    {
        List<Project> projects = new ArrayList<>();

        for (Project project : projectRepository.findAll())
        {
            projects.add(project);
        }

        model.addAttribute("projects", projects);
        return "mainPage";
    }

    @PostMapping("/projects")
    public String postMainPageController(@RequestParam String title, @RequestParam String description, @RequestParam String startDate, Model model)
    {
        LocalDate temp_start_date = LocalDate.parse(startDate);

        if (title != null && !title.isEmpty() &&
            description != null && !description.isEmpty() &&
            startDate != null && !startDate.isEmpty()
        )
        {
            Project tempProject = new Project(title, description, temp_start_date);
            projectRepository.save(tempProject);
            model.addAttribute("projectAdded", true);
        }
        else
        {
            model.addAttribute("projectNotAdded", true);
        }


        List<Project> projects = new ArrayList<>();
        for (Project el : projectRepository.findAll())
        {
            projects.add(el);
        }
        model.addAttribute("projects", projects);

        return "mainPage";
    }

    @GetMapping("/registration")
    public String getRegistrationPageController()
    {
        return "registrationPage";
    }

    @PostMapping("/registration")
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


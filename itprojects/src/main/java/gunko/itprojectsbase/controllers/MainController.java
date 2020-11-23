package gunko.itprojectsbase.controllers;

import gunko.itprojectsbase.database.Project;
import gunko.itprojectsbase.database.User;
import gunko.itprojectsbase.database.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/projects")
public class MainController
{
    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping
    public String getMainPageController(
            @RequestParam(required = false) String filter,
            Model model)
    {
        if(filter != null)
        {
           try
           {
               LocalDate temp_start_date = LocalDate.parse(filter);
           }
           catch(DateTimeParseException ex)
           {
               //log
           }
        }
        else
        {
            filter = "";
        }

        List<Project> projects = new ArrayList<>();

        if(filter.isEmpty())
        {
            for (Project project : projectRepository.findAll())
            {
                projects.add(project);
            }
        }
        else
        {
            for (Project project : projectRepository.findAll())
            {
                if (!filter.isEmpty() && (project.getTitle().contains(filter) ||
                     project.getAuthor().getUsername().contains(filter) ||
                     project.getStartDate().toString().compareTo(filter) == 0))
                {
                    projects.add(project);
                }
            }
        }

        model.addAttribute("projects", projects);
        model.addAttribute("filter", filter);
        return "mainPage";
    }

    @PostMapping
    public String postMainPageController(
            @AuthenticationPrincipal User user,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String startDate,
            Model model)
    {

        if (title != null && !title.isEmpty() &&
            description != null && !description.isEmpty() &&
            startDate != null && !startDate.isEmpty()
        )
        {
            LocalDate temp_start_date = LocalDate.parse(startDate);

            Project tempProject = new Project(title, description, temp_start_date, user);
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
}


package gunko.itprojectsbase.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Project
{
    @Id
    @GeneratedValue()
    private Integer id;

    private String title;
    private String description;
    private LocalDate startDate;

    public Integer getId()
    {
        return id;
    }

    public Project()
    {
    }

    public Project(String title, String description, LocalDate startDate)
    {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public LocalDate getStartDate()
    {
        return startDate;
    }

    public void setStartDate(LocalDate startDate)
    {
        this.startDate = startDate;
    }
}

package gunko.itprojectsbase.database;

import javax.persistence.*;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    public Project()
    {
    }

    public Project(String title, String description, LocalDate startDate, User author)
    {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.author = author;
    }

    public Integer getId()
    {
        return id;
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

    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }
}

package gunko.itprojectsbase.database;

import javax.persistence.*;

@Entity
@Table(name = "usr")
public class User
{
    @Id
    @GeneratedValue()
    private Integer id;

    private String username;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean active;
    private String userCV;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String login)
    {
        this.username = login;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Role getRole()
    {
        return role;
    }

    public void setRole(Role role)
    {
        this.role = role;
    }

    public Boolean getActivated()
    {
        return active;
    }

    public void setActivated(Boolean activated)
    {
        active = activated;
    }

    public String getUserCV()
    {
        return userCV;
    }

    public void setUserCV(String userCV)
    {
        this.userCV = userCV;
    }
}

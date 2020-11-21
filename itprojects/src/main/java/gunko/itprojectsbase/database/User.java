package gunko.itprojectsbase.database;

import javax.persistence.*;

@Entity
public class User
{
    @Id
    @GeneratedValue()
    private Integer id;

    private String login;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean isActivated;
    private String userCV;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
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
        return isActivated;
    }

    public void setActivated(Boolean activated)
    {
        isActivated = activated;
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

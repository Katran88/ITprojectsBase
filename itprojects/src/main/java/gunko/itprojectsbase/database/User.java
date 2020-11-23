package gunko.itprojectsbase.database;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "usr")
public class User implements UserDetails
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

    public Integer getId() { return id; }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return getActivated();
    }

    public void setUsername(String login)
    {
        this.username = login;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        Collection<Role> roles = new ArrayList<Role>();
        roles.add(getRole());

        return roles;
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

package gunko.itprojectsbase.service;

import gunko.itprojectsbase.database.Role;
import gunko.itprojectsbase.database.User;
import gunko.itprojectsbase.database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

@Service
public class UserService implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailSender mailSender;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        return userRepository.findByUsername(username);
    }

    public boolean addUser(User user)
    {
        if(userRepository.findByUsername(user.getUsername()) != null)
        {
            return false;
        }

        user.setActivated(false);
        user.setRole(Role.User);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        if(!user.getEmail().isEmpty())
        {
            String message = String.format("Hello, %s! \n" +
                                           "Welcome to the IT Projects Base \n" +
                                           "Visit this link to activate your account: http://localhost:8080/registration/activate/%s",
                                           user.getUsername(), user.getActivationCode());

            mailSender.send(user.getEmail(), "Activation code", message);
        }

        return true;
    }

    public boolean activateUser(String code)
    {
        User user = userRepository.findByActivationCode(code);
        if(user == null)
        {
            return false;
        }

        user.setActivationCode(null);
        user.setActivated(true);
        userRepository.save(user);

        return true;
    }
}

package gunko.itprojectsbase.database.repositories;

import gunko.itprojectsbase.database.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>
{
    User findByUsername(String username);
}

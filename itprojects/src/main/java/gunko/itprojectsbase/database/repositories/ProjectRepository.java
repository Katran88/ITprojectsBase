package gunko.itprojectsbase.database.repositories;

import gunko.itprojectsbase.database.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Integer> {}
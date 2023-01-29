package space.sedov.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.sedov.server.entity.Course;
import space.sedov.server.entity.Module;

import java.util.List;
import java.util.Optional;

public interface ModuleRepository extends JpaRepository<Module, Integer> {
    public Optional<Module> findById(Integer id);
    public List<Module> findModuleByCourse(Course course);
}

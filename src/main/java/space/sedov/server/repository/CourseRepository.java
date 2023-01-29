package space.sedov.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.sedov.server.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    public List<Course> findAll();
    public Optional<Course> findById(Integer id);
}

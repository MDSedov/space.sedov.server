package space.sedov.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.sedov.server.entity.Course;
import space.sedov.server.entity.Lesson;
import space.sedov.server.entity.Module;

import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    public Optional<Lesson> findById(Integer id);
    public List<Lesson> findLessonsByCourse(Course course);
    public List<Lesson> findLessonsByModule(Module module);
}

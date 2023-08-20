package space.sedov.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.sedov.server.entity.Lesson;
import space.sedov.server.entity.Module;
import space.sedov.server.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    public Optional<Task> findById(Integer id);

    public List<Task> findTasksByModule(Module module);

    public List<Task> findTasksByLessonOrderByNumber(Lesson lesson);
}

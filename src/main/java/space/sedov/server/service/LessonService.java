package space.sedov.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.sedov.server.entity.Lesson;
import space.sedov.server.entity.Task;
import space.sedov.server.repository.LessonRepository;
import space.sedov.server.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService implements LessonServiceInterface{
    @Autowired
    private final LessonRepository lessonRepository;

    @Autowired
    private final TaskRepository taskRepository;

    public LessonService(LessonRepository lessonRepository,
                         TaskRepository taskRepository) {
        this.lessonRepository = lessonRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    @Override
    public Lesson getLessonById(Integer id) {
        Lesson lesson = null;
        Optional<Lesson> optional = lessonRepository.findById(id);
        if (optional.isPresent()) {
            lesson = optional.get();
        }
        return lesson;
    }

    @Override
    public List<Task> getAllTasksByLesson(int id) {
        Optional<Lesson> optional = lessonRepository.findById(id);
        if (optional.isPresent()) {
            Lesson lesson = optional.get();
            return taskRepository.findTasksByLesson(lesson);
        }
        return null;
    }
}

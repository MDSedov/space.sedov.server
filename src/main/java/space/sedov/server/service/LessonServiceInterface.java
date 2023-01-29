package space.sedov.server.service;

import space.sedov.server.entity.Lesson;
import space.sedov.server.entity.Task;

import java.util.List;

public interface LessonServiceInterface {
    public List<Lesson> getAllLessons();

    public Lesson getLessonById(Integer id);

    public List<Task> getAllTasksByLesson(int id);
}

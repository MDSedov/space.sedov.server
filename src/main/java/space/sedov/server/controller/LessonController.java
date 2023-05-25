package space.sedov.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import space.sedov.server.entity.Lesson;
import space.sedov.server.entity.Task;
import space.sedov.server.service.lesson.LessonServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/lesson")
public class LessonController {
    @Autowired
    private LessonServiceImpl lessonServiceImpl;

    @GetMapping("/{id}")
    public Lesson getLessonById(@PathVariable int id) {
        return lessonServiceImpl.getLessonById(id);
    };

    @GetMapping("/{id}/tasks")
    public List<Task> getAllTasksByLessonId(@PathVariable int id) {
        return lessonServiceImpl.getAllTasksByLesson(id);
    }
}

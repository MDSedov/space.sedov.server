package space.sedov.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import space.sedov.server.entity.Lesson;
import space.sedov.server.entity.Task;
import space.sedov.server.service.LessonService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class LessonController {
    @Autowired
    private LessonService lessonService;

    @GetMapping("/api/lessons/{id}")
    public Lesson getLessonById(@PathVariable int id) {
        return lessonService.getLessonById(id);
    };

    @GetMapping("/api/lessons/{id}/tasks")
    public List<Task> getAllTasksByLessonId(@PathVariable int id) {
        return lessonService.getAllTasksByLesson(id);
    }
}

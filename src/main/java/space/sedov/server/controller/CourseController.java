package space.sedov.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import space.sedov.server.entity.Course;
import space.sedov.server.entity.Lesson;
import space.sedov.server.entity.Module;
import space.sedov.server.service.CourseService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/api/courses")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/api/courses/{id}")
    public Course getCourseById(@PathVariable int id) {
        return courseService.getCourseById(id);
    }

    @GetMapping("/api/courses/{id}/lessons")
    public List<Lesson> getLessonsByCourseId(@PathVariable int id) {
        return courseService.getAllLessonsByCourse(id);
    }

    @GetMapping("/api/courses/{id}/modules")
    public List<Module> getModulesByCourseId(@PathVariable int id) {
        return courseService.getAllLessonsByModule(id);
    }
}

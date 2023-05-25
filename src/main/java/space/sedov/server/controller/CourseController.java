package space.sedov.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import space.sedov.server.entity.Course;
import space.sedov.server.entity.Lesson;
import space.sedov.server.entity.Module;
import space.sedov.server.service.course.CourseServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    private CourseServiceImpl courseServiceImpl;

    @GetMapping("/")
    public List<Course> getAllCourses() {
        return courseServiceImpl.getAllCourses();
    }

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable int id) {
        return courseServiceImpl.getCourseById(id);
    }

    @GetMapping("/{id}/lessons")
    public List<Lesson> getLessonsByCourseId(@PathVariable int id) {
        return courseServiceImpl.getAllLessonsByCourse(id);
    }

    @GetMapping("/{id}/modules")
    public List<Module> getModulesByCourseId(@PathVariable int id) {
        return courseServiceImpl.getAllLessonsByModule(id);
    }
}

package space.sedov.server.service;

import space.sedov.server.entity.Course;
import space.sedov.server.entity.Lesson;
import space.sedov.server.entity.Module;

import java.util.List;

public interface CourseServiceInterface {
    public List<Course> getAllCourses();

    public List<Lesson> getAllLessonsByCourse(Integer id);

    public Course getCourseById(Integer id);

    public List<Module> getAllLessonsByModule(Integer id);
}

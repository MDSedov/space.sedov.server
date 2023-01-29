package space.sedov.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.sedov.server.entity.Course;
import space.sedov.server.entity.Lesson;
import space.sedov.server.entity.Module;
import space.sedov.server.repository.CourseRepository;
import space.sedov.server.repository.LessonRepository;
import space.sedov.server.repository.ModuleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService implements CourseServiceInterface {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private final LessonRepository lessonRepository;

    @Autowired
    private final ModuleRepository moduleRepository;

    public CourseService(CourseRepository courseRepository,
                         LessonRepository lessonRepository,
                         ModuleRepository moduleRepository) {
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
        this.moduleRepository = moduleRepository;
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public List<Lesson> getAllLessonsByCourse(Integer id) {
        Optional<Course> optional = courseRepository.findById(id);
        if (optional.isPresent()) {
            Course course = optional.get();
            return lessonRepository.findLessonsByCourse(course);
        }
        return null;
    }

    @Override
    public List<Module> getAllLessonsByModule(Integer id) {
        Optional<Course> optional = courseRepository.findById(id);
        if (optional.isPresent()) {
            Course course = optional.get();
            return (moduleRepository.findModuleByCourse(course));
        }
        return null;
    }

    @Override
    public Course getCourseById(Integer id) {
        Course course = null;
        Optional<Course> optional = courseRepository.findById(id);
        if (optional.isPresent()) {
            course = optional.get();
        }
        return course;
    }
}

package space.sedov.server.service;

import space.sedov.server.entity.Lesson;
import space.sedov.server.entity.Module;
import space.sedov.server.entity.Task;

import java.util.List;

public interface ModuleServiceInterface {
    public Module getModuleById(Integer id);
    public List<Lesson> getAllLessonsByModule(Integer id);

    public List<Task> getAllTasksByModule(Integer id);
}

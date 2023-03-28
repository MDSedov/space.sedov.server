package space.sedov.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.sedov.server.entity.Lesson;
import space.sedov.server.entity.Module;
import space.sedov.server.entity.Task;
import space.sedov.server.repository.LessonRepository;
import space.sedov.server.repository.ModuleRepository;
import space.sedov.server.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ModuleService implements ModuleServiceInterface {
    @Autowired
    private final ModuleRepository moduleRepository;

    @Autowired
    private final LessonRepository lessonRepository;

    @Autowired
    private final TaskRepository taskRepository;

    public ModuleService(ModuleRepository moduleRepository,
                         LessonRepository lessonRepository,
                         TaskRepository taskRepository) {
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public Module getModuleById(Integer id) {
        Optional<Module> optional = moduleRepository.findById(id);
        if (optional.isPresent()) {
            Module module = optional.get();
            return module;
        }
        return null;
    }

    @Override
    public List<Lesson> getAllLessonsByModule(Integer id) {
        Optional<Module> optional = moduleRepository.findById(id);
        if (optional.isPresent()) {
            Module module = optional.get();
            return lessonRepository.findLessonsByModuleOrderByNumber(module);
        }
        return null;
    }

    @Override
    public List<Task> getAllTasksByModule(Integer id) {
        Optional<Module> optional = moduleRepository.findById(id);
        if (optional.isPresent()) {
            Module module = optional.get();
            return taskRepository.findTasksByModule(module);
        }
        return null;
    }
}

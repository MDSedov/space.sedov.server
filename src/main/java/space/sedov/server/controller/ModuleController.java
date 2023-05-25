package space.sedov.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import space.sedov.server.entity.Module;
import space.sedov.server.entity.Task;
import space.sedov.server.service.lesson.LessonServiceImpl;
import space.sedov.server.service.module.ModuleServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/module")
public class ModuleController {
    @Autowired
    private final ModuleServiceImpl moduleServiceImpl;

    public ModuleController(ModuleServiceImpl moduleServiceImpl, LessonServiceImpl lessonServiceImpl) {
        this.moduleServiceImpl = moduleServiceImpl;
    }

    @GetMapping("/{id}")
    public Module getModuleById(@PathVariable int id) {
        return moduleServiceImpl.getModuleById(id);
    }

    @GetMapping("/{id}/tasks")
    public List<Task> getAllTasksByModuleId(@PathVariable int id) {
        return moduleServiceImpl.getAllTasksByModule(id);
    }

}

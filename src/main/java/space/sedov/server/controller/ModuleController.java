package space.sedov.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import space.sedov.server.entity.Module;
import space.sedov.server.entity.Task;
import space.sedov.server.service.LessonService;
import space.sedov.server.service.ModuleService;

import java.util.List;

@RestController
public class ModuleController {
    @Autowired
    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService, LessonService lessonService) {
        this.moduleService = moduleService;
    }

    @GetMapping("/api/modules/{id}")
    public Module getModuleById(@PathVariable int id) {
        return moduleService.getModuleById(id);
    }

    @GetMapping("/api/modules/{id}/tasks")
    public List<Task> getAllTasksByModuleId(@PathVariable int id) {
        return moduleService.getAllTasksByModule(id);
    }

}

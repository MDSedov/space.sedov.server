package space.sedov.server.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import space.sedov.server.entity.Task;
import space.sedov.server.service.TaskService;

@RestController
@CrossOrigin(origins = "*")
public class TaskController {
    @Autowired
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/api/tasks/{id}")
    public Task getModuleById(@PathVariable int id) {
        return taskService.getTaskById(id);
    }

    @RequestMapping(
            value = "/api/tasks/{id}/check",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String check(@PathVariable int id, @RequestBody String userAnswer) {
        System.out.println(id);
        Task task = taskService.getTaskById(id);
        if (task.getType().equals("test")) {
            System.out.println(task.getAnswer());
            System.out.println(userAnswer);
            return taskService.getTestResult(task.getAnswer(), userAnswer).toString();
        } else if (task.getType().equals("request")) {
            return taskService.getRequestResult(task.getAnswer(), userAnswer).toString();
        } else {
            return new JSONObject().put("Error", "User answer type is incorrect").toString();
        }
    }
}

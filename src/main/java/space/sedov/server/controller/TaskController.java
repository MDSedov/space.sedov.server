package space.sedov.server.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import space.sedov.server.entity.Task;
import space.sedov.server.service.task.TaskServiceImpl;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    private final TaskServiceImpl taskServiceImpl;

    public TaskController(TaskServiceImpl taskServiceImpl) {
        this.taskServiceImpl = taskServiceImpl;
    }

    @GetMapping("/{id}")
    public Task getModuleById(@PathVariable int id) {
        return taskServiceImpl.getTaskById(id);
    }

    @PostMapping(value = "/{id}/check", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String check(@PathVariable int id, @RequestBody String userAnswer) {
        System.out.println(id);
        Task task = taskServiceImpl.getTaskById(id);
        if (task.getType().equals("test")) {
            System.out.println(task.getAnswer());
            System.out.println(userAnswer);
            return taskServiceImpl.getTestResult(task.getAnswer(), userAnswer).toString();
        } else if (task.getType().equals("request")) {
            return taskServiceImpl.getRequestResult(task.getAnswer(), userAnswer).toString();
        } else {
            return new JSONObject().put("Error", "User answer type is incorrect").toString();
        }
    }
}

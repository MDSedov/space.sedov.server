package space.sedov.server.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import space.sedov.server.entity.Task;
import space.sedov.server.service.response.MessageService;
import space.sedov.server.service.response.ResponseService;
import space.sedov.server.service.task.TaskRequest;
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
    public ResponseService getTaskById(@PathVariable int id) {
        return taskServiceImpl.getTaskById(id);
    }

    @PostMapping(value = "/{id}/check", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseService check(@PathVariable int id, @RequestBody TaskRequest taskRequest) {
        Task task = (Task) taskServiceImpl.getTaskById(id).getResponseObject();
        if (task.getType().equals("test")) {
            return taskServiceImpl.getTestResult(task.getAnswer(), taskRequest.getAnswer());
        } else if (task.getType().equals("request")) {
            return taskServiceImpl.getRequestResult(task.getAnswer(), taskRequest.getAnswer());
        } else {
            return new ResponseService(HttpStatus.BAD_REQUEST, MessageService.TASK_INCORRECT_REQUEST_TYPE, MessageService.TASK_INCORRECT_REQUEST_TYPE.toString());
        }
    }
}

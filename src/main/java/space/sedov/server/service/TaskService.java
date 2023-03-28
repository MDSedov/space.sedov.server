package space.sedov.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.sedov.server.entity.Task;
import space.sedov.server.repository.SqlCourseRepository;
import space.sedov.server.repository.TaskRepository;

import java.sql.Connection;
import java.util.Optional;

@Service
public class TaskService implements TaskServiceInterface{
    @Autowired
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task getTaskById(Integer id) {
        Task task = null;
        Optional<Task> optional = taskRepository.findById(id);
        if (optional.isPresent()) {
            task = optional.get();
        }
        return task;
    }

    @Override
    public JSONObject getTestResult(String correctAnswer, String userAnswer) {
        JSONObject result = new JSONObject();
        if (userAnswer.contains(correctAnswer)) {
            result.put("result", "true");
        } else {
            result.put("result", "false");
        }
        return result;
    }

    @Override
    public JSONObject getRequestResult(String correctQuery, String userQuery) {
        SqlCourseRepository sqlCourseRepository = new SqlCourseRepository();

        JSONObject result = new JSONObject();

        JSONArray userAnswer = sqlCourseRepository.executeQuery(userQuery);
        result.put("userAnswer", userAnswer);

        JSONArray correctAnswer = sqlCourseRepository.executeQuery(correctQuery);
        result.put("correctAnswer", correctAnswer);

//        ObjectMapper mapper = new ObjectMapper();
//        assertEquals(mapper.readTree(userAnswer.toString()), mapper.readTree(correctAnswer.toString()));

        return result;
    }
}

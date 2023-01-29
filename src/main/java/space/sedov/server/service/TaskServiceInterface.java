package space.sedov.server.service;

import org.json.JSONArray;
import org.json.JSONObject;
import space.sedov.server.entity.Task;

public interface TaskServiceInterface {
    public Task getTaskById(Integer id);
    public JSONObject getTestResult(String correctAnswer, String userAnswer);
    public JSONObject getRequestResult(String correctQuery, String userQuery);
}

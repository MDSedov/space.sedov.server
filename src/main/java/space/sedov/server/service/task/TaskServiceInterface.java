package space.sedov.server.service.task;

import org.json.JSONObject;
import space.sedov.server.entity.Task;
import space.sedov.server.service.response.ResponseService;

public interface TaskServiceInterface {
    public ResponseService getTaskById(Integer id);
    public ResponseService getTestResult(String correctAnswer, String userAnswer);
    public ResponseService getRequestResult(String correctQuery, String userQuery);
}

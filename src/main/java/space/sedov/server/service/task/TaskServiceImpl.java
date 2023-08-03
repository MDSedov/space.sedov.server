package space.sedov.server.service.task;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import space.sedov.server.entity.Task;
import space.sedov.server.repository.SqlCourseRepository;
import space.sedov.server.repository.TaskRepository;
import space.sedov.server.service.response.MessageService;
import space.sedov.server.service.response.ResponseService;

import java.util.Objects;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskServiceInterface{
    @Autowired
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public ResponseService getTaskById(Integer id) {
        try {
            Task task = null;
            Optional<Task> optional = taskRepository.findById(id);
            if (optional.isPresent()) {
                task = optional.get();
            }
            return new ResponseService(HttpStatus.OK, MessageService.OK, task);
        } catch (Exception e) {
            return new ResponseService(HttpStatus.BAD_REQUEST, MessageService.UNKNOWN_PROBLEM, e.getMessage());
        }
    }

    @Override
    public ResponseService getTestResult(String correctAnswer, String userAnswer) {
        try {
            if (userAnswer.isEmpty()) {
                return new ResponseService(HttpStatus.BAD_REQUEST, MessageService.TASK_USER_ANSWER_IS_EMPTY, MessageService.TASK_USER_ANSWER_IS_EMPTY.toString());
            }
            JSONObject result = new JSONObject();
            if (userAnswer.contains(correctAnswer)) {
                result.put("taskSuccess", true);
            } else {
                result.put("taskSuccess", false);
            }
            return new ResponseService(HttpStatus.OK, MessageService.OK, result.toString());
        } catch (Exception e) {
            return new ResponseService(HttpStatus.BAD_REQUEST, MessageService.UNKNOWN_PROBLEM, e.getMessage());
        }
    }

    @Override
    public ResponseService getRequestResult(String correctQuery, String userQuery) {
        try {
            if (userQuery.isEmpty()) {
                return new ResponseService(HttpStatus.BAD_REQUEST, MessageService.TASK_SQL_REQUEST_IS_EMPTY, MessageService.TASK_SQL_REQUEST_IS_EMPTY.toString());
            }
            SqlCourseRepository sqlCourseRepository = new SqlCourseRepository();
            JSONObject result = new JSONObject();

            //Выполняем SQL запрос полученный от пользователя
            ResponseService requestAnswer = sqlCourseRepository.executeQuery(userQuery);
            if (requestAnswer.getResponseCode().equals(HttpStatus.BAD_REQUEST)) {
                return requestAnswer;
            }
            result.put("taskRequestAnswer", requestAnswer.getResponseObject());

            //Выполняем правильный SQL запрос из ответа на задание
            ResponseService correctAnswer = sqlCourseRepository.executeQuery(correctQuery);
            if (correctAnswer.getResponseCode().equals(HttpStatus.BAD_REQUEST)) {
                return correctAnswer;
            }
            result.put("taskCorrectAnswer", correctAnswer.getResponseObject());

            //Сравниваем результаты двух SQL запросов
            boolean success = Objects.equals(requestAnswer.getResponseObject().toString(), correctAnswer.getResponseObject().toString());
            result.put("taskSuccess", success);

            return new ResponseService(HttpStatus.OK, MessageService.OK, result.toString());
        } catch (NullPointerException e) {
            return new ResponseService(HttpStatus.BAD_REQUEST, MessageService.TASK_SQL_REQUEST_IS_EMPTY, MessageService.TASK_SQL_REQUEST_IS_EMPTY.toString());
        } catch (Exception e) {
            return new ResponseService(HttpStatus.BAD_REQUEST, MessageService.UNKNOWN_PROBLEM, e.getMessage());
        }
    }
}

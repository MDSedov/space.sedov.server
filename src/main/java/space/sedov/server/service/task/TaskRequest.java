package space.sedov.server.service.task;

public class TaskRequest {
    private Integer id;
    private String answer;

    public TaskRequest() {
    }

    public TaskRequest(Integer id, String answer) {
        setId(id);
        setAnswer(answer);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

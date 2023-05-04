package space.sedov.server.service.authentication;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ResponseService<T> {
    private HttpStatus responseCode;
    private MessageResponseService responseMessage;
    private T responseObject;

    public ResponseService() {}

    public ResponseService(HttpStatus code, MessageResponseService msg) {
        this.responseCode = code;
        this.responseMessage = msg;
    }

    public ResponseService(HttpStatus code, MessageResponseService msg, T responseObject) {
        this.responseCode = code;
        this.responseMessage = msg;
        this.responseObject = responseObject;
    }

    public HttpStatus getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(HttpStatus responseCode) {
        this.responseCode = responseCode;
    }

    public MessageResponseService getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(MessageResponseService responseMessage) {
        this.responseMessage = responseMessage;
    }

    public T getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(T responseObject) {
        this.responseObject = responseObject;
    }

    public void setSuccessResponse() {
        this.setResponseCode(HttpStatus.OK);
        this.setResponseMessage(MessageResponseService.OK);
    }

    public void setInternalServerErrorResponse() {
        this.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR);
        this.setResponseMessage(MessageResponseService.TRANSACTION_PROBLEM);
    }
}

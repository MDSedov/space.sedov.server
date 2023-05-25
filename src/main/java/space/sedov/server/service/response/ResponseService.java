package space.sedov.server.service.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.Map;

@Service
public class ResponseService<T> {
    private HttpStatus responseCode;
    private MessageService responseMessage;
    private T responseObject;

    public ResponseService() {}

    public ResponseService(HttpStatus code, MessageService msg) {
        this.responseCode = code;
        this.responseMessage = msg;
    }
    public ResponseService(HttpStatus code, BindingResult bindingResult) {
        this.responseCode = code;
        String errorValidationMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
        this.responseMessage = MessageService.valueOf(errorValidationMessage);
    }

    public ResponseService(HttpStatus code, MessageService msg, T responseObject) {
        this.responseCode = code;
        this.responseMessage = msg;
        this.responseObject = responseObject;
    }

    //Получаем значения полей
    public HttpStatus getResponseCode() {
        return responseCode;
    }
    public MessageService getResponseMessage() {
        return responseMessage;
    }
    public T getResponseObject() {
        return responseObject;
    }

    //Устанавливаем значение полей
    public void setResponseCode(HttpStatus responseCode) {
        this.responseCode = responseCode;
    }
    public void setResponseMessage(MessageService responseMessage) {
        this.responseMessage = responseMessage;
    }
    public void setResponseObject(T responseObject) {
        this.responseObject = responseObject;
    }
}

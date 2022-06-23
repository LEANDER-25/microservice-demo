package com.phunghung29.microservice.commons.response;

import com.phunghung29.microservice.commons.exceptions.ExceptionResponse;
import lombok.Getter;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Getter
public class ResponseTemplate {
    ResponseInformation information;
    Map<String, Object> response;

    private ResponseTemplate() {
        response = new HashMap<>();
        response.put("information", getInformation());
    }

    public ResponseTemplate(HttpStatus status) {
        this.information = new ResponseInformation(status.value());
        new ResponseTemplate();
    }

    public ResponseTemplate(int status) {
        this.information = new ResponseInformation(status);
        new ResponseTemplate();
    }

    public ResponseTemplate(boolean isSuccess, int status, Object body) {
        this.information = new ResponseInformation(status);
        response = new HashMap<>();
        response.putAll(information.getInformation());
        if (isSuccess) setSuccessResponse(body); else setErrorResponse((ExceptionResponse) body);
    }

    private void setSuccessResponse(Object body) {
        this.response.put("data", body);
    }

    private void setErrorResponse(ExceptionResponse exception) {
        Map<String, Object> body = new HashMap<>();
        body.put("errorCode", exception.getErrorCode());
        body.put("errorType", exception.getErrorType());
        body.put("message", exception.getMessage());
        this.response.put("error", body);
    }

    public static ResponseTemplate success(int status, Object body) {
        return new ResponseTemplate(true, status, body);
    }

    public static ResponseTemplate success(Object body) {
        return new ResponseTemplate(true, 200, body);
    }

    public static ResponseTemplate error(int status, Object body) {
        return new ResponseTemplate(false, status, body);
    }

    public ResponseEntity<Map<String, Object>> release(){
        return ResponseEntity.status(information.getStatusCode()).contentType(MediaType.APPLICATION_JSON).body(response);
    }
    public HttpServletResponse releaseAsServlet(HttpServletResponse defaultResponse) {
        defaultResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        JSONObject jsonObject = new JSONObject(response);
        try {
            defaultResponse.setStatus(information.getStatusCode());
            defaultResponse.getWriter().write(jsonObject.toString());
            return defaultResponse;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

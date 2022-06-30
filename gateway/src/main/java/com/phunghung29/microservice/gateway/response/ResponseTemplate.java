package com.phunghung29.microservice.gateway.response;

import com.phunghung29.microservice.gateway.exceptions.CustomRuntimeException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ResponseTemplate {
    ResponseInformation information;
    Object data;
    ExceptionResponse error;
    String message;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExceptionResponse {
        private String statusCode;
        private String errorCode;
        private String errorType;
    }

    private ResponseTemplate() {
    }

    public ResponseEntity<ResponseTemplate> release() {
        return ResponseEntity.status(information.getStatusCode()).body(this);
    }

    public HttpServletResponse releaseAsServlet(HttpServletResponse defaultResponse) {
        defaultResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Map<String, Object> response = new HashMap<>();
        response.put("information", information);
        if (error != null) {
            response.put("error", error);
        } else {
            response.put("data", data);
            response.put("message", message);
        }
        JSONObject jsonObject = new JSONObject(response);
        try {
            defaultResponse.setStatus(information.getStatusCode());
            defaultResponse.getWriter().write(jsonObject.toString());
            return defaultResponse;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public interface DefaultResponseBuilder {
        DefaultResponseBuilder setStatus(int status);
        DefaultResponseBuilder setStatus(HttpStatus status);
        ResponseTemplate build();
        ResponseEntity<ResponseTemplate> release();
    }

    public interface SuccessResponseBuilder extends DefaultResponseBuilder {
        SuccessResponseBuilder setData(Object data);
        SuccessResponseBuilder setMessage(String message);
    }

    public interface ErrorResponseBuilder extends DefaultResponseBuilder {
        ErrorResponseBuilder setError(Object error);
    }

    public static class DefaultResponseBuilderImpl implements DefaultResponseBuilder {

        ResponseTemplate responseTemplate;

        public DefaultResponseBuilderImpl() {
            this.responseTemplate = new ResponseTemplate();
            this.responseTemplate.setInformation(new ResponseInformation());
        }

        @Override
        public DefaultResponseBuilderImpl setStatus(int status) {
            this.responseTemplate.getInformation().setStatusCode(status);
            return this;
        }

        @Override
        public DefaultResponseBuilderImpl setStatus(HttpStatus status) {
            this.responseTemplate.getInformation().setStatusCode(status.value());
            return this;
        }

        @Override
        public ResponseTemplate build() {
            return this.responseTemplate;
        }

        @Override
        public ResponseEntity<ResponseTemplate> release() {
            return ResponseEntity.status(this.responseTemplate.getInformation().getStatusCode()).body(this.responseTemplate);
        }
    }

    public static class SuccessResponseBuilderImpl extends DefaultResponseBuilderImpl implements SuccessResponseBuilder {
        public SuccessResponseBuilderImpl() {
            super();
            this.responseTemplate.getInformation().setStatusCode(200);
        }

        public SuccessResponseBuilderImpl(Object data) {
            super();
            this.responseTemplate.getInformation().setStatusCode(200);
            setData(data);
        }

        public SuccessResponseBuilderImpl(int status, Object data) {
            super();
            this.responseTemplate.getInformation().setStatusCode(status);
            setData(data);
        }

        @Override
        public SuccessResponseBuilder setData(Object data) {
            this.responseTemplate.setData(data);
            return this;
        }

        @Override
        public SuccessResponseBuilder setMessage(String message) {
            this.responseTemplate.setMessage(message);
            return this;
        }
    }

    public static class ErrorResponseBuilderImpl extends DefaultResponseBuilderImpl implements ErrorResponseBuilder {
        public ErrorResponseBuilderImpl() {
            super();
        }

        public ErrorResponseBuilderImpl(Object error) {
            super();
            setError(error);
        }

        public ErrorResponseBuilderImpl(int status, Object error) {
            super();
            this.responseTemplate.getInformation().setStatusCode(status);
            setError(error);
        }

        @Override
        public ErrorResponseBuilder setError(Object error) {
            ExceptionResponse exceptionResponse = new ExceptionResponse();
            BeanUtils.copyProperties(error, exceptionResponse);
            this.responseTemplate.setError(exceptionResponse);
            return this;
        }
    }

    public static SuccessResponseBuilder success() {
        return new SuccessResponseBuilderImpl();
    }

    public static SuccessResponseBuilder success(Object data) {
        return new SuccessResponseBuilderImpl(data);
    }

    public static SuccessResponseBuilder success(int status, Object data) {
        return new SuccessResponseBuilderImpl(status, data);
    }

    public static ErrorResponseBuilder error() {
        return new ErrorResponseBuilderImpl();
    }

    public static ErrorResponseBuilder error(Object error) {
        return new ErrorResponseBuilderImpl(error);
    }

    public static ErrorResponseBuilder error(int status, Object error) {
        return new ErrorResponseBuilderImpl(status, error);
    }
}

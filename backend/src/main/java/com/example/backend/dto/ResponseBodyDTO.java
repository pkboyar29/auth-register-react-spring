package com.example.backend.dto;

public class ResponseBodyDTO {
    private String message;
    private String error_code;
    private Object body;
    public ResponseBodyDTO() {}
    public ResponseBodyDTO(Object body, String message, String error_code) {
        this.message = message;
        this.body = body;
        this.error_code = error_code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Object getBody() {
        return body;
    }
    public void setBody(Object body) {
        this.body = body;
    }
    public void setError_code(String error_code) {
        this.error_code = error_code;
    }
    public String getError_code() {
        return error_code;
    }
}

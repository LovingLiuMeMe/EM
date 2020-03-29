package cn.lovingliu.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerResponse<T> implements Serializable {
    private static final long serialVersionUID = -7104817146255245711L;

    private int status;
    private String msg;
    private T data;

    private ServerResponse(int status) {
        this.status = status;
    }

    private ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public boolean ifSuccess() {
        return this.status == 200;
    }

    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public static <T> ServerResponse<T> createBySuccess() {
        return new ServerResponse<T>(200);
    }

    public static <T> ServerResponse<T> createBySuccessMessage(String msg) {
        return new ServerResponse<T>(200, msg);
    }

    public static <T> ServerResponse<T> createBySuccess(T data) {
        return new ServerResponse<T>(200, data);
    }

    public static <T> ServerResponse<T> createBySuccess(String msg, T data) {
        return new ServerResponse<T>(200, msg, data);
    }


    public static <T> ServerResponse<T> createByError() {
        return new ServerResponse<T>(500, "error");
    }
    public static <T> ServerResponse<T> createByErrorMap(T errorMap) {
        return new ServerResponse<T>(500, "error", errorMap);
    }
    public static <T> ServerResponse<T> createByErrorMessage(String errorMsg) {
        return new ServerResponse<T>(500, errorMsg);
    }

    public static <T> ServerResponse<T> createByErrorStatusMessage(int errorStatus, String errorMsg) {
        return new ServerResponse<T>(errorStatus, errorMsg);
    }
}

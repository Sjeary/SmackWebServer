package org.example.smackwebserver;

public class Response <T>{

    private T data;

    private String errorMsg;

    private boolean success;
    public static <K> Response<K> newSuccess(K data){
        Response<K> response = new Response<>();
        response.setData(data);
        response.setSuccess(true);
        return response;
    }
    public static <K>Response<K> newFail(String errorMsg){
        Response<K> response =new Response<>();
        response.setErrorMsg(errorMsg);
        response.setSuccess(false);
        response.setData(null);
        return response;
    }
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}

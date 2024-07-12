package org.example.login_app.utils;

public class Result<T> {
    private String code;
    private String msg;
    private String captcha; // 新增验证码字段
    private int errorCount;
    private T data;
//code的getter&setter
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
//msg的getter&setter
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
//data的getter&setter
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
//Captcha的getter&setter
    public String getCaptcha() {
        return captcha;
    }
    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
//errorCount的getter&setter
    public int getErrorCount() {
        return errorCount;
    }
    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }
//Result构造函数的重载
    public Result() {
    }

    public Result(T data) {
        this.data = data;
    }

//success
    public static <T> Result<T> success(T data, String msg, int errorCount) {
        Result<T> result = new Result<>(data);
        result.setCode("1");
        result.setMsg(msg);
        result.setErrorCount(errorCount);
        return result;
    }
//error

    public static Result error(String code, String msg, int errorCount) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setErrorCount(errorCount);
        return result;
    }
}

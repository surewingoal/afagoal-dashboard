package com.afagoal.util;

public class Response<T> {

    public static final Response success = Response.normal(0, null, null);
    public static final Response errorSys = Response.normal(0x00001, null,"后台服务接收数据失败");
    public static final Response errorJson = Response.normal(0x00002, null,"JSON解析错误");
    public static final Response errorApi = Response.normal(0x00004, null,"未定义的协议");
    public static final Response errorDb = Response.normal(0x00006, null,"数据库操作失败");
    public static final Response errorAuth = Response.normal(0x00008, null,"请登录后进行操作");
    public static final Response errorAuth1 = Response.normal(0x00009, null,"token错误");
    public static final Response errorAuth2 = Response.normal(0x0000a, null,"token已过期，请重新登录");

    private String msg;

    private int rc;

    private T data;

    public Response() {
    }

    private Response(int rc, T data, String msg) {
        this.rc = rc;
        this.data = data;
        this.msg = msg;
    }

    public static <R> Response<R> ok(R object){
        return new Response<R>(0, object, null);
    }

    public static <R> Response<R> error(int code, String message){
        return new Response<R>(code, null, message);
    }

    public static <R> Response<R> error(String message) {
        return new Response<R>(1, null, message);
    }

    public static <R> Response<R> normal(int code, R object, String message){
        return new Response<R>(code, object, message);
    }

    @Override
    public String toString() {
        return "Response{" +
                "msg='" + msg + '\'' +
                ", rc=" + rc +
                ", data=" + data +
                '}';
    }

    public static Response<Void> errorParam(String argName) {
        return error(3, "错误的" + argName + "参数");
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getRc() {
        return rc;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

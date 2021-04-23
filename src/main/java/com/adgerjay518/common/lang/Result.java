package com.adgerjay518.common.lang;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {//序列化
    /**
     * 200是正常，非200表示异常
     */
    private int code;
    private String msg;
    private Object data;

    public static Result success(int code, String msg, Object data){
        Result r=new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
    public static Result success(Object data){
        return success(200,"操作成功",data);
    }
    public static Result error(int code,String msg,Object data){
        Result r=new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
    public static Result error(String msg){
        return error(400,msg,null);
    }
}

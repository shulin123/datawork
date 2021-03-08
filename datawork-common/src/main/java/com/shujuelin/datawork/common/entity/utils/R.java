package com.shujuelin.datawork.common.entity.utils;

import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * description : 返回 数据
 *
 * @param :
 * @author : tanxingsong
 * @date : 15:00 2018/11/5
 * @email : tanxingsong@cetcbigdata.com
 * @return :
 */
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
        super.put("code", CommonCode.SUCCESS.code());
        super.put("msg", CommonCode.SUCCESS.msg());
    }

    public static R error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }


    public static R error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }


    public static R error(int retCode, String retMsg) {
        R r = new R();
        r.put("code", retCode);
        r.put("msg", retMsg);
        return r;
    }

    public static R ok(String retMsg) {
        R r = new R();
        r.put("msg", retMsg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    public R put(Object value) {
        super.put("result", value);
        return this;
    }

    public R putObject(String key, Object value) {
        super.put("result", (new HashMap<String, Object>()).put(key, value));
        return this;
    }
}

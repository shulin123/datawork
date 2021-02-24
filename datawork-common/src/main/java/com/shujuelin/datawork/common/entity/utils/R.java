package com.shujuelin.datawork.common.entity.utils;


import com.shujuelin.datawork.common.entity.exception.DataWorkException;
import com.shujuelin.datawork.common.entity.exception.RRException;
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
        super.put("code", com.bdri.commons.tools.response.CommonCode.SUCCESS.code());
        super.put("msg", com.bdri.commons.tools.response.CommonCode.SUCCESS.msg());
    }

    public static R error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static R error(ResultCode resultCode, String msg) {
        R r = new R();
        r.put("code", resultCode.code());
        r.put("msg", msg);
        return r;
    }

    public static R error(ResultCode resultCode) {
        R r = new R();
        r.put("code", resultCode.code());
        r.put("msg", resultCode.msg());
        return r;
    }

    public static R error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static R error(ErrorCodeEnum errorCodeEnum) {
        R r = new R();
        r.put("code", errorCodeEnum.getCode());
        r.put("msg", errorCodeEnum.getMsg());
        return r;
    }

    public static R error(ErrorCodeEnum errorCodeEnum, String msg) {
        R r = new R();
        r.put("code", errorCodeEnum.getCode());
        r.put("msg", msg);
        return r;
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


    public static Object transfer(R r) {
        if (Integer.parseInt(r.get("code").toString()) == ErrorCodeEnum.SUCCESS.getCode()) {
            if (r.containsKey("result")) {
                return r.get("result");
            }
        } else {
            throw new RRException(r.get("msg").toString(), Integer.parseInt(r.get("code").toString()));
        }
        return null;
    }
}

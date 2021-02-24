package com.shujuelin.datawork.common.entity.exception;


import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.shujuelin.datawork.common.entity.utils.ErrorCodeEnum;
import com.shujuelin.datawork.common.entity.utils.R;
import com.shujuelin.datawork.common.entity.utils.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.MessageUtils;

/**
 * 自定义异常
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午10:11:27
 */
public class RRException extends RuntimeException {
    private Logger logger = LoggerFactory.getLogger(RRException.class);

    private static final long serialVersionUID = 1L;

    private String msg;
    private int    code = 500;
    private String errorMsg;

    /**
     * description : 抛出异常
     */
    public static void cast(String msg, ErrorCodeEnum errorCodeEnum) {
        throw new RRException(msg, errorCodeEnum.getCode());
    }

    /**
     * description : 抛出异常
     */
    public static void cast(String msg, ResultCode resultCode) {
        throw new RRException(msg, resultCode.code());
    }

    /**
     * description : 抛出异常
     */
    public static void cast(ResultCode resultCode, String msg) {
        throw new RRException(resultCode, msg);
    }

    /**
     * description : 抛出异常
     */
    public static void cast(ResultCode resultCode) {
        throw new RRException(resultCode.msg(), resultCode.code());
    }

    /**
     * description : 自定义异常
     */
    public RRException(ResultCode resultCode) {
        super(resultCode.msg());
        this.msg = resultCode.msg();
        this.code = resultCode.code();
    }

    /**
     * description : 抛出异常
     */
    public static void cast(ErrorCodeEnum errorCodeEnum) {
        throw new RRException(errorCodeEnum.getMsg(), errorCodeEnum.getCode());
    }

    /**
     * description : 自定义异常
     */
    public RRException(ResultCode resultCode, String msg) {
        super(msg);
        this.msg = msg;
        this.code = resultCode.code();
    }

    /**
     * description : 自定义异常
     */
    public RRException(ErrorCodeEnum errorCodeEnum, String msg) {
        super(msg);
        this.msg = msg;
        this.code = errorCodeEnum.getCode();
    }

    /**
     * description : 自定义异常
     */
    public RRException(ErrorCodeEnum errorCodeEnum, String msg, String errorMsg) {
        super(msg);
        this.msg = msg;
        this.code = errorCodeEnum.getCode();
        this.errorMsg = errorMsg;
    }

    public RRException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public RRException(JSONObject result) {
        super(result.toJSONString());
        String msg = "";
        Integer code = 10000;
        try {
            msg = result.getString("msg");
            code = result.getInteger("code");
        } catch (JSONException e) {
            logger.error("获取code和msg异常：" + result.toJSONString());
        }
        this.msg = msg;
        this.code = code;
    }


    public RRException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public RRException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public RRException(Integer code, String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public RRException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}

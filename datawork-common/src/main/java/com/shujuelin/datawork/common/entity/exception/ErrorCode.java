package com.shujuelin.datawork.common.entity.exception;

/**
 * @author : shujuelin
 * @date : 20:27 2021/2/9
 */
//返回的状态
public class ErrorCode {
    //系统正常返回
    public static final int SYSTEM_SUCCESS = 200;
    //系统异常
    public static final int SYSTEM_EXCEPTION = 100;
    //系统输入参数异常
    public static final int ERROR_PARAM = 101;
    // 用户不存在
    public static final int ERROR_USER_NOT_EXISTS = 102;
    //密码错误
    public static final int ERROR_PASSWORD = 103;
    //权限不足
    public static final int ERROR_PERMMISION = 104;
}

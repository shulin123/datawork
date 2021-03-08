package com.shujuelin.datawork.common.entity.utils;

import lombok.ToString;

/**
 * 10000-- 通用错误代码
 * 23000-- 用户中心错误代码
 *
 * @author : tanxingsong
 * @date : 8:49 2019/6/18
 * @email : tanxingsong@cetcbigdata.com
 */

@ToString
public enum CommonCode implements ResultCode {
    SUCCESS(10000, "操作成功！"),
    UNAUTHENTICATED(10001, "此操作需要登录系统！"),
    UNAUTHORISE(10002, "权限不足，无权操作！"),
    INVALID_PARAM(10003, "非法参数！"),
    REQUEST_METHOD_NOT_SUPPORTED_ERROR(10004, "请求方法不支持！"),
    FAIL(11111, "操作失败！"),
    REMOTE_CALL_FAIL(10005, "远程调用失败！"),
    FEIGN_CALL_ERROR(10006, "调用feign请求异常！"),
    REMOTE_CALL_FALLBACK(10007, "微服务调用熔断！"),
    REQUEST_METHOD_ERROR(10008, "请求方法不支持: "),
    LICENSE_EXPIRE(10009, "license过期，请联系管理员续租!"),
    LICENSE_KEY_ERROR(10010, "license key 错误!"),
    SERVER_ERROR(99999, "抱歉，系统繁忙，请稍后重试！"),

    ;
    /**
     * 操作代码
     */
    int    code;
    /**
     * 提示信息
     */
    String msg;

    private CommonCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String msg() {
        return msg;
    }


}

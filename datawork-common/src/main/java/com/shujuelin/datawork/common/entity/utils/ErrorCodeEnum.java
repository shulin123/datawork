package com.shujuelin.datawork.common.entity.utils;

/**
 * @author : shujuelin
 * @date : 0:17 2021/2/13
 */
public enum ErrorCodeEnum {
    SUCCESS(10000, "成功"),
    ERROR(500, "发生错误"),
    UNKNOWN_ERROR(10001, "未知错误"),
    SERVICE_TEMPORARY_UNAVAILABLE(10002, "服务暂不可用"),
    UNSUPPORTED_OPEN_API_METHOD(10003, "未知的方法"),
    OPEN_API_REQUEST_LIMIT_REACHED(10004, "接口调用次数已达到设定的上限"),
    UNAUTHORIZED_CLIENT_IP_ADDRESS(10005, "请求来自未经授权的IP地址"),
    NO_PERMISSION_TO_ACCESS_DATA(10006, "无权限访问该用户数据"),
    N0_PERMISSION_TO_ACCESS_DATA_FOR_THIS_REFERER(10007, "来自该refer的请求无访问权限"),
    INVALID_PARAMETER(10008, "请求参数无效"),
    INVALID_API_KEY(10009, "api key无效"),
    SESSION_KEY_INVALID_OR_NO_LONGER_VALID(10010, "session key无效"),
    INCORRECT_SIGNATURE(10011, "无效签名"),
    TOO_MANY_PARAMETERS(10012, "请求参数过多"),
    UNSUPPORTED_SIGNATURE_METHOD(10013, "未知的签名方法"),
    INVALID_USER_ID(10014, "无效的user id"),
    ACCESS_TOKEN_INVALID(10015, "无效的access token"),
    ACCESS_TOKEN_EXPIRED(10016, "access token过期"),
    SESSION_KEY_EXPIRED(10017, "session key过期"),
    INVALID_IP(10018, "无效的ip参数"),
    DB_ERROR(10100, "数据库异常"),
    DB_CONNECT_ERROR(10101, "数据库连接异常"),
    DB_SQL_ERROR(10102, "执行sql异常"),
    DB_AUTHENTICATION_ERROR(10103, "数据库权限异常"),
    DB_DATABASE_ERROR(10104, "数据库异常"),
    DB_TABLE_ERROR(10105, "数据表异常"),
    DB_PARAMETER_ERROR(10106, "数据库字段异常"),
    DB_INSERT_ERROR(10107, "插入操作异常"),
    DB_DELETE_ERROR(10108, "修改操作异常"),
    DB_UPDATE_ERROR(10109, "删除操作异常"),
    DB_OBJECT_NOT_EXIST(10110, "对象不存在"),
    DB_OBJECT_ALREADY_EXIST(10111, "对象已经存在"),
    FILE_READ_IO_ERROR(10201, "读取文件io错误"),
    FILE_UPLOAD_ERROR(10202, "上传文件异常"),
    FILE_DELETE_ERROR(10203, "删除文件异常"),
    FILE_TYPE_ERROR(10204, "文件类型错误"),
    FILE_CONTENT_ERROR(10205, "文件内容不符"),
    REST_POST_ERROR(10301, "调用远程post请求异常"),
    REST_GET_ERROR(10302, "调用远程get请求异常"),
    REST_CONNECTION_TIME_OUT_ERROR(10303, "调用远程服务连接超时"),
    REST_RESPONSE_INFO_ERROR(10304, "调用远程服务返回信息异常"),
    MODULE_KETTLE_ERROE(10401, "kettle模块运行异常"),
    MODULE_DATA_QUALITY_ERROR(10501, "数据质量管理模块异常"),
    BUSSINESS_ERROR(20000, "业务错误");

    private int code;
    private String msg;

    private ErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

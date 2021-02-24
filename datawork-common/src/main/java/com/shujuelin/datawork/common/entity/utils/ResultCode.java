package com.shujuelin.datawork.common.entity.utils;

/**
 * 10000-- 通用错误代码
 * 20000-- 数据科学平台
 * 30000-- 数据工场
 * 40000-- 数据治理平台错误码
 * - 41000-- 数据源管理模块错误码
 * - 42000-- 数据资源管理模块错误码
 * - 43000-- 编目服务模块错误码
 * - 44000-- 流数据管理模块错误码
 * - 45000-- 数据模型管理模块错误码
 * - 46000-- 文件管理错误码
 *
 * @author : tanxingsong
 * @date : 8:49 2019/6/18
 * @email : tanxingsong@cetcbigdata.com
 */
public interface ResultCode {
    /**
     * description : 操作代码
     */
    int code();

    /**
     * description : 提示信息
     */
    String msg();

}

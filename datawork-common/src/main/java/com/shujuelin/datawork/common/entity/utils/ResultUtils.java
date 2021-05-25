package com.shujuelin.datawork.common.entity.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.shujuelin.datawork.common.entity.constant.Constant;
import com.shujuelin.datawork.common.entity.exception.DataWorkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author : xingsongtan@qq.com
 * @date : 9:19 2019/9/20
 */
public class ResultUtils {
    private static Logger log = LoggerFactory.getLogger(ResultUtils.class);

    /**
     * @return true : 校验通过 ，false ：校验异常
     */
    public static boolean checkResult(R result) {
        // 如果返回的结果不等于成功标识，则抛出异常
        if (null == result || !result.get(Constant.REST_CODE).equals(CommonCode.SUCCESS.code())) {
            log.error("校验查询结果异常:{}", JSON.toJSONString(result));
            return false;
        }
        return true;
    }

    /**
     * 解析返回结果，并返回指定对象
     */
    public static <T> T getObjectFromResult(R result, Class<T> target) {
        if (result == null) {
            return null;
        }
        String objString = JSON.toJSONString(result.get(Constant.REST_RESULT));
        T resultObj = null;
        try {
            resultObj = JSON.parseObject(objString, target);
        } catch (Exception e) {
            log.error("analysis result error ", e);
        }
        return resultObj;
    }

    /**
     * 解析返回结果，并返回指定列表
     */
    public static <T> List<T> getListFromResult(R result, Class<T> target) {
        if (result == null) {
            return null;
        }
        String objString = JSON.toJSONString(result.get(Constant.REST_RESULT));
        List<T> resultObj = null;
        try {
            resultObj = JSONArray.parseArray(objString, target);
        } catch (Exception e) {
            log.error("analysis result error ", e);
        }
        return resultObj;
    }

    /**
     * 解析返回结果，并返回已校验指定对象
     */
    public static <T> T getCheckedObjectFromResult(R result, Class<T> target) {
        if (result == null) {
            return null;
        }
        if (!checkResult(result)) {
            throw new DataWorkException(result.get(Constant.REST_MSG).toString(),Integer.parseInt(result.get(Constant.REST_CODE).toString()) ,null);
        }
        return getObjectFromResult(result, target);
    }

    /**
     * 解析返回结果，并返回指定列表
     */
    public static <T> List<T> getCheckedListFromResult(R result, Class<T> target) {
        if (result == null) {
            return null;
        }
        if (!checkResult(result)) {
            throw new DataWorkException(result.get(Constant.REST_MSG).toString(),Integer.parseInt(result.get(Constant.REST_CODE).toString()) ,null);
        }
        return getListFromResult(result, target);
    }
}

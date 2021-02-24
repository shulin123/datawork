package com.shujuelin.datawork.common.entity.controller;


import com.shujuelin.datawork.common.entity.exception.DataWorkException;
import com.shujuelin.datawork.common.entity.exception.ErrorCode;
import org.apache.http.HttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : shujuelin
 * @date : 20:44 2021/2/9
 */
@ControllerAdvice
public class BaseController {

    //时间格式化
  protected  SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @ExceptionHandler
  @ResponseBody
  public Map<String,Object> exceptionHandler(Exception ex, HttpServletResponse response){

      //检测是否自定义的异常
      if (DataWorkException.class.isAssignableFrom(ex.getClass())){

          DataWorkException ng = (DataWorkException) ex;
          return getResultMap(ng.getErrorCode(),ng.getErrorMessage(),null);

      }else {

          return getError(ErrorCode.SYSTEM_EXCEPTION, ex.getMessage());
      }
  }

  protected Map<String,Object> getError(int errCode,String errMsg){
      return getResultMap(errCode,null,null);
  }

  protected Map<String,Object> getResultMap(Integer code,Object data,Map<String,Object> extraMap){
      String currentTime = sdf.format(new Date());
      HashMap<String,Object> result = new HashMap<>();
      result.put("currentTime",currentTime);
      if (code == null || code.equals(ErrorCode.SYSTEM_SUCCESS)){
          result.put("code",ErrorCode.SYSTEM_SUCCESS);
          result.put("data",data);
      }else {
          result.put("code",code);
          result.put("msg",data);
      }

      if (extraMap != null && !extraMap.isEmpty()){

          result.putAll(extraMap);
      }
      return result;
  }

}

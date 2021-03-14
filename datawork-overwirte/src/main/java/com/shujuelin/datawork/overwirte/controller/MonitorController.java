package com.shujuelin.datawork.overwirte.controller;

import cn.hutool.core.date.DateUtil;
import com.shujuelin.datawork.common.entity.controller.BaseController;
import com.shujuelin.datawork.common.entity.utils.R;
import com.shujuelin.datawork.overwirte.entity.HdfsSummaryEntity;
import com.shujuelin.datawork.overwirte.entity.YarnSummaryEntity;
import com.shujuelin.datawork.overwirte.service.MonitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author : shujuelin
 * @date : 22:01 2021/2/12
 */
@RestController
@RequestMapping("datawork/v1/monitor")
@Api("大数据平台之数据概览")
//跨域
@CrossOrigin
public class MonitorController  {

    @Autowired
    MonitorService monitorService;

    /**
     * 获取当前时间hdfs的summary
     * @return
     */
    @ApiOperation("Hdf")
    @GetMapping(value = "/storage")
    public R getHdfsSummary(){

    HdfsSummaryEntity hdfsSummaryEntity = monitorService.findHdfsSummary((int) DateUtil.currentSeconds());
    return R.ok().put(hdfsSummaryEntity);
    }

    /**
     * 获取当前时间yar的summary
     * @return
     */
    @GetMapping(value = "/calc")
    public R getYarnSummary() {
        YarnSummaryEntity yarnSummaryEntity = monitorService.findYarnSummary((int) DateUtil.currentSeconds());
        return R.ok().put(yarnSummaryEntity);
    }

    /**
     * hdfs图表
     * @return
     */
    @GetMapping(value = "/storage/chart")
    public R getHdfsSummaryList() {
        long current = System.currentTimeMillis();
        //获取0点数据   getRawOffset用于获取以毫秒为单位的时间量添加到UTC在这个时间段获得的标准时间
        long zero = current - TimeZone.getDefault().getRawOffset();
        List<HdfsSummaryEntity> hdfsSummaryBetween = monitorService.findHdfsSummaryBetween((int) (zero / 1000), (int) (current / 1000));
        List<String> columns = Arrays.stream(FieldUtils.getAllFields(HdfsSummaryEntity.class))
                .map(Field::getName).collect(Collectors.toList());
        Map<String, Object> data = new HashMap<>();
        data.put("rows", hdfsSummaryBetween);
        data.put("columns", columns);
        return R.ok(data);
    }

    /**
     * yarn的图表
     * @return
     */
    @GetMapping(value = "/calc/chart")
    public R getYarnSummaryList() {
        //获取当前时间
        long current = System.currentTimeMillis();
        long zero = current - TimeZone.getDefault().getRawOffset();

        List<YarnSummaryEntity> yarnSummaryBetween = monitorService.findYarnSummaryBetween((int) (zero/1000), (int) (current/1000));

        //通过反射拿到列
        List<String> columns = Arrays.stream(FieldUtils.getAllFields(YarnSummaryEntity.class)).map(Field::getName).collect(Collectors.toList());

        Map<String, Object> data = new HashMap<>();
        data.put("columns", columns);
        data.put("rows", yarnSummaryBetween);
        return R.ok(data);
    }
}

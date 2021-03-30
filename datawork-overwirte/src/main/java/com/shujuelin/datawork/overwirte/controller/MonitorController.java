package com.shujuelin.datawork.overwirte.controller;

import cn.hutool.core.date.DateUtil;
import com.shujuelin.datawork.common.entity.controller.BaseController;
import com.shujuelin.datawork.common.entity.utils.R;
import com.shujuelin.datawork.overwirte.entity.HdfsSummaryEntity;
import com.shujuelin.datawork.overwirte.entity.QueueMetricsEntity;
import com.shujuelin.datawork.overwirte.entity.YarnSummaryEntity;
import com.shujuelin.datawork.overwirte.service.MonitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
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
    @ApiOperation("HDFS的指标")
    @GetMapping(value = "/storage")
    public R getHdfsSummary(){

    HdfsSummaryEntity hdfsSummaryEntity = monitorService.findHdfsSummary(new Date());
    return R.ok().put(hdfsSummaryEntity);
    }

    /**
     * 获取当前时间yar的summary
     * @return
     */
    @ApiOperation("yarn的指标")
    @GetMapping(value = "/calc")
    public R getYarnSummary() {
        YarnSummaryEntity yarnSummaryEntity = monitorService.findYarnSummary(new Date());
        return R.ok().put(yarnSummaryEntity);
    }

    /**
     * hdfs图表
     * @return
     */
    @ApiOperation("HDFS存储变化曲线")
    @GetMapping(value = "/storage/chart")
    public R getHdfsSummaryList() {
        long current = System.currentTimeMillis();
        //获取当前日期的0点时刻
        long zero = current/(1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();
        List<HdfsSummaryEntity> hdfsSummaryBetween = monitorService.findHdfsSummaryBetween(zero,current);
        List<String> columns = Arrays.stream(FieldUtils.getAllFields(HdfsSummaryEntity.class))
                .map(Field::getName).collect(Collectors.toList());
        Map<String, Object> data = new HashMap<>();
        data.put("rows", hdfsSummaryBetween);
        data.put("columns", columns);
        return R.ok().put(data);
    }

    /**
     * 队列的状态图
     * @return
     */
    @ApiOperation("YARN的状态图")
    @GetMapping(value = "/calc/queue")
    public R getYarnSummaryList() {

        List<QueueMetricsEntity> yarnSummaryBetween = monitorService.findQueueMetrics(new Date());

        //通过反射拿到列
        List<String> columns = Arrays.stream(FieldUtils.getAllFields(QueueMetricsEntity.class)).map(Field::getName).collect(Collectors.toList());

        Map<String, Object> data = new HashMap<>();
        data.put("columns", columns);
        data.put("rows", yarnSummaryBetween);
        return R.ok().put(data);
    }


}

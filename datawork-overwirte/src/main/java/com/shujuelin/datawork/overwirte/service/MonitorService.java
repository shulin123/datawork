package com.shujuelin.datawork.overwirte.service;


import com.shujuelin.datawork.overwirte.entity.HdfsSummaryEntity;
import com.shujuelin.datawork.overwirte.entity.QueueMetricsEntity;
import com.shujuelin.datawork.overwirte.entity.YarnSummaryEntity;

import java.util.List;

/**
 * @author : shujuelin
 * @date : 14:54 2021/2/10
 */

public interface MonitorService {

    //添加hdfs summary
    void addHdfsSummary(HdfsSummaryEntity hdfsSummaryEntity);

    //添加yarn summary
    void addYarnSummary(YarnSummaryEntity yarnSummaryEntity);

    //添加queue metric
    void addQueueMetrics(List<QueueMetricsEntity> queueMetricEntities);

    //根据时间查找最近一次的hdfs summary
    HdfsSummaryEntity findHdfsSummary(int selectTime);

    //根据时间查找最近一次的yarn summary
    YarnSummaryEntity findYarnSummary(int selectTime);

    //根据时间查找最近一次的queue metric
    List<QueueMetricsEntity> findQueueMetrics(int selectTime);

    //查询某段时间hdfs summary
    List<HdfsSummaryEntity> findHdfsSummaryBetween(int startTime, int endTime);

    //查询某段时间yarn summary
    List<YarnSummaryEntity> findYarnSummaryBetween(int startTime, int endTime);
}

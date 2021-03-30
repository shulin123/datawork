package com.shujuelin.datawork.overwirte.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.shujuelin.datawork.overwirte.dao.HdfsSummaryDao;
import com.shujuelin.datawork.overwirte.dao.QueueMetricsDao;
import com.shujuelin.datawork.overwirte.dao.YarnSummaryDao;
import com.shujuelin.datawork.overwirte.entity.HdfsSummaryEntity;
import com.shujuelin.datawork.overwirte.entity.QueueMetricsEntity;
import com.shujuelin.datawork.overwirte.entity.YarnSummaryEntity;
import com.shujuelin.datawork.overwirte.service.MonitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author : shujuelin
 * @date : 14:58 2021/2/10
 */
@Service
public class MonitorServiceImpl extends ServiceImpl<HdfsSummaryDao, HdfsSummaryEntity> implements MonitorService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    YarnSummaryDao yarnSummaryDao;
    @Autowired
    QueueMetricsDao queueMetricsDao;

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    /**
     * 增加hdfsSummary信息
     * @param hdfsSummaryEntity
     */
    @Override
    public void addHdfsSummary(HdfsSummaryEntity hdfsSummaryEntity) {
        baseMapper.insert(hdfsSummaryEntity);
    }

    /**
     * 增加yarn的信息
     * @param yarnSummaryEntity
     */
    @Override
    public void addYarnSummary(YarnSummaryEntity yarnSummaryEntity) {
        yarnSummaryDao.insert(yarnSummaryEntity);
    }

    /**
     * 增加队列信息
     * @param queueMetricEntities
     */
    @Override
    public void addQueueMetrics(List<QueueMetricsEntity> queueMetricEntities) {
        for (QueueMetricsEntity metrics: queueMetricEntities) {
            queueMetricsDao.insert(metrics);
        }
    }

    /**
     * 查询hdfs概要详细信息
     * @param selectTime
     * @return
     */
    @Override
    public HdfsSummaryEntity findHdfsSummary(Date selectTime) {
        //mybatis-plus的查询
        QueryWrapper<HdfsSummaryEntity> hdfsSummaryWrapper = new QueryWrapper<>();
        hdfsSummaryWrapper.eq("create_time",df.format(selectTime));
        HdfsSummaryEntity hdfsSummaryEntity = baseMapper.selectOne(hdfsSummaryWrapper);
        return hdfsSummaryEntity;
    }

    /**
     * yarn概览详情
     * @param selectTime
     * @return
     */
    @Override
    public YarnSummaryEntity findYarnSummary(Date selectTime) {
        QueryWrapper<YarnSummaryEntity> yarnSummaryWrapper = new QueryWrapper<>();
        yarnSummaryWrapper.eq("create_time",df.format(selectTime));
        logger.info(df.format(selectTime));
        YarnSummaryEntity yarnSummaryEntity = yarnSummaryDao.selectOne(yarnSummaryWrapper);
        return yarnSummaryEntity;
    }



    /**
     * 时间段的查询hdfs概要
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<HdfsSummaryEntity> findHdfsSummaryBetween(Long startTime, Long endTime) {
        QueryWrapper<HdfsSummaryEntity> queryWrapperList = new QueryWrapper<>();
        //大于等于
        queryWrapperList.ge("create_time",df.format(startTime));
        //小于等于
        queryWrapperList.le("create_time",df.format(endTime));
        logger.info("start" +df.format(startTime));
        logger.info("end" +df.format(endTime));
        List<HdfsSummaryEntity> hdfsSummaryList = baseMapper.selectList(queryWrapperList);
        return hdfsSummaryList;
    }

    /**
     * 获取yarn时间段的概要
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<YarnSummaryEntity> findYarnSummaryBetween(Long startTime, Long endTime) {
        QueryWrapper<YarnSummaryEntity> queryWrapperList = new QueryWrapper<>();
        //大于等于
        queryWrapperList.ge("create_time",df.format(startTime));
        //小于等于
        queryWrapperList.le("create_time",df.format(endTime));
        List<YarnSummaryEntity> yarnSummaryList = yarnSummaryDao.selectList(queryWrapperList);
        return yarnSummaryList;
    }

    /**
     * 队列概要详情
     * @param selectTime
     * @return
     */
    @Override
    public List<QueueMetricsEntity> findQueueMetrics(Date selectTime) {

        QueryWrapper<QueueMetricsEntity> queueMetricsQueryWrapper = new QueryWrapper<>();
        queueMetricsQueryWrapper.eq("create_time",df.format(selectTime));
        List<QueueMetricsEntity> queueMetricsList  = queueMetricsDao.selectList(queueMetricsQueryWrapper);
        return queueMetricsList;
    }

}

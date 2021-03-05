package com.shujuelin.datawork.overwirte.schedule;

import cn.hutool.core.date.DateUtil;
import com.shujuelin.datawork.common.entity.utils.StatefulHttpClient;
import com.shujuelin.datawork.common.entity.constant.constant;
import com.shujuelin.datawork.overwirte.entity.HdfsSummaryEntity;
import com.shujuelin.datawork.overwirte.entity.QueueMetricsEntity;
import com.shujuelin.datawork.overwirte.entity.YarnSummaryEntity;
import com.shujuelin.datawork.overwirte.service.MonitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.*;

/**
 * @author : shujuelin
 * @date : 18:02 2021/2/10
 * 定时调度获取hadoop的监控信息
 */
@Component
public class HadoopJmxSchedule {

    private Logger logger = LoggerFactory.getLogger(getClass());

    //namenode的url
    @Value("${custom.hadoop.nn.uri}")
    private String nnUriStr;
    //ResourceManager的url
    @Value("${custom.hadoop.rm.uri}")
    private String rmUriStr;

    @Autowired
    MonitorService monitorService;
    //通过StatefulHttpClient去访问hadoop的jmx的url
    private StatefulHttpClient client = new StatefulHttpClient(null);

    //获取active namenode uri
    private String getActiveNameNodeUri(List<String> nameNodeUri) throws IOException {
        String activeNameNodeUri = nameNodeUri.get(0);
        //判断nameNodeUri是否多个
        if (nameNodeUri.size() > 1) {
            for (String uri : nameNodeUri) {
                //字符串常规类型格式化   动态格式
                String fsNameSystemUrl = String.format(constant.JMXSERVERURLFORMAT, uri, constant.FSNAMESYSTEM);
                //获取到bean实体
                HadoopMetrics hadoopMetrics = client.get(HadoopMetrics.class, fsNameSystemUrl, null, null);
                if (hadoopMetrics.getMetricsValue("tag.HAState").toString().equals("active")) {
                    activeNameNodeUri = uri;
                    break;
                }
            }
        }
        return activeNameNodeUri;
    }

    //获取active resource manager uri
    private String getActiveRmUri(List<String> rmUris) throws IOException {
        String activeRmUri = rmUris.get(0);
        if (rmUris.size() > 1) {
            for (String uri : rmUris) {
                String clusterMetricsUrl = String.format(constant.JMXSERVERURLFORMAT, uri, constant.CLUSTERMETRICS);
                HadoopMetrics hadoopMetrics = client.get(HadoopMetrics.class, clusterMetricsUrl, null, null);
                if (hadoopMetrics.getMetricsValue("tag.ClusterMetrics").toString().equals("ResourceManager")) {
                    activeRmUri = uri;
                    break;
                }
            }
        }
        return activeRmUri;
    }


    //定时执行 获取jmx信息
    @Scheduled(cron = "* * * * * ?")
    public void hadoopMetricsCollect() {
        //收集hdfs jmx监控信息
        try {
            HdfsSummaryEntity hdfsSummaryEntity = reportHdfsSummary(client);
            if (hdfsSummaryEntity != null) {
                monitorService.addHdfsSummary(hdfsSummaryEntity);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        //收集yarn jmx监控信息
        YarnSummaryEntity yarnSummaryEntity = reportYarnSummaryEntity(client);
        if (yarnSummaryEntity != null) {
            monitorService.addYarnSummary(yarnSummaryEntity);
        }
        //收集队列的jmx

        try {
            List<QueueMetricsEntity> queueMetricsEntities = queryQueueMetrics();
            monitorService.addQueueMetrics(queueMetricsEntities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取yarn的概要信息
     * @param client
     * @return
     */
    private YarnSummaryEntity reportYarnSummaryEntity(StatefulHttpClient client) {
        YarnSummaryEntity yarnSummaryEntity = new YarnSummaryEntity();
        List<String> rmUris = Arrays.asList(rmUriStr.split(";"));
        if (rmUris.isEmpty()) {
            yarnSummaryEntity.setTrash(true);
            return yarnSummaryEntity;
        }
        try {
            String clusterMetricsUrl = String.format(constant.JMXSERVERURLFORMAT, getActiveRmUri(rmUris), constant.CLUSTERMETRICS);
            HadoopMetrics clusterMetrics = client
                    .get(HadoopMetrics.class, clusterMetricsUrl, null, null);
            if (clusterMetrics.getMetricsValue("tag.ClusterMetrics").toString()
                    .equals("ResourceManager")) {

                yarnSummaryEntity
                        .setLiveNodeManagerNums((int) clusterMetrics.getMetricsValue("NumActiveNMs"));
                yarnSummaryEntity.setDeadNodeManagerNums((int) clusterMetrics.getMetricsValue("NumLostNMs"));
                yarnSummaryEntity
                        .setUnhealthyNodeManagerNums(
                                (int) clusterMetrics.getMetricsValue("NumUnhealthyNMs"));

                String queueMetricsUrl = String.format(constant.JMXSERVERURLFORMAT, getActiveRmUri(rmUris), constant.QUEUEMETRICS);
                HadoopMetrics hadoopMetrics = client
                        .get(HadoopMetrics.class, queueMetricsUrl, null, null);
                yarnSummaryEntity.setSubmittedApps((int) hadoopMetrics.getMetricsValue("AppsSubmitted"));
                yarnSummaryEntity.setRunningApps((int) hadoopMetrics.getMetricsValue("AppsRunning"));
                yarnSummaryEntity.setPendingApps((int) hadoopMetrics.getMetricsValue("AppsPending"));
                yarnSummaryEntity.setCompletedApps((int) hadoopMetrics.getMetricsValue("AppsCompleted"));
                yarnSummaryEntity.setKilledApps((int) hadoopMetrics.getMetricsValue("AppsKilled"));
                yarnSummaryEntity.setFailedApps((int) hadoopMetrics.getMetricsValue("AppsFailed"));
                yarnSummaryEntity.setAllocatedMem(
                        Long.parseLong(hadoopMetrics.getMetricsValue("AllocatedMB").toString()));
                yarnSummaryEntity.setAllocatedCores((int) hadoopMetrics.getMetricsValue("AllocatedVCores"));
                yarnSummaryEntity
                        .setAllocatedContainers((int) hadoopMetrics.getMetricsValue("AllocatedContainers"));
                yarnSummaryEntity.setAvailableMem(
                        Long.parseLong(hadoopMetrics.getMetricsValue("AvailableMB").toString()));
                yarnSummaryEntity.setAvailableCores((int) hadoopMetrics.getMetricsValue("AvailableVCores"));
                yarnSummaryEntity
                        .setPendingMem(
                                Long.parseLong(hadoopMetrics.getMetricsValue("PendingMB").toString()));
                yarnSummaryEntity.setPendingCores((int) hadoopMetrics.getMetricsValue("PendingVCores"));
                yarnSummaryEntity
                        .setPendingContainers((int) hadoopMetrics.getMetricsValue("PendingContainers"));
                yarnSummaryEntity
                        .setReservedMem(
                                Long.parseLong(hadoopMetrics.getMetricsValue("ReservedMB").toString()));
                yarnSummaryEntity.setReservedCores((int) hadoopMetrics.getMetricsValue("ReservedVCores"));
                yarnSummaryEntity
                        .setReservedContainers((int) hadoopMetrics.getMetricsValue("ReservedContainers"));

                yarnSummaryEntity.setCreateTime((int) DateUtil.currentSeconds());
                yarnSummaryEntity.setTrash(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            yarnSummaryEntity.setTrash(true);
        }
        return yarnSummaryEntity;
    }

    /**
     * 获取hdfs的概要信息
     * @param client
     * @return
     * @throws IOException
     */
    private HdfsSummaryEntity reportHdfsSummary(StatefulHttpClient client) throws IOException {
        //调用get active namenode uri
        List<String> nameNodeUris = Arrays.asList(nnUriStr.split(";"));
        if (nameNodeUris.isEmpty()) {
            return null;
        }
        //得到活跃的namenode的uri
        String activeNameNodeUri = getActiveNameNodeUri(nameNodeUris);
        HdfsSummaryEntity hdfsSummaryEntity = new HdfsSummaryEntity();
        try {
            //拼接url
            String nameNodeInfoUrl = String.format(constant.JMXSERVERURLFORMAT,
                    activeNameNodeUri, constant.NAMENODEINFO);
            //获取metrics
            HadoopMetrics hadoopMetrics = client.get(HadoopMetrics.class,
                    nameNodeInfoUrl, null, null);
             hdfsSummaryEntity
                    .setTotal(Long.parseLong(
                           hadoopMetrics.getMetricsValue("Total").toString()));
            hdfsSummaryEntity
                    .setDfsUsed(Long.parseLong(hadoopMetrics.getMetricsValue("Used").toString()));
            //hdfs使用百分比
            hdfsSummaryEntity.setPercentUsed(
                    Float.parseFloat(hadoopMetrics.getMetricsValue("PercentUsed").toString()));
            hdfsSummaryEntity
                    .setDfsFree(Long.parseLong(hadoopMetrics.getMetricsValue("Free").toString()));
            //配置的dfs的空间有多少空间被不是hdfs的文件占用了的
            hdfsSummaryEntity.setNonDfsUsed(
                    Long.parseLong(hadoopMetrics.getMetricsValue("NonDfsUsedSpace").toString()));
            hdfsSummaryEntity.setTotalBlocks(
                    Long.parseLong(hadoopMetrics.getMetricsValue("TotalBlocks").toString()));
            hdfsSummaryEntity
                    .setTotalFiles(Long.parseLong(hadoopMetrics.getMetricsValue("TotalFiles").toString()));
            hdfsSummaryEntity.setMissingBlocks(
                    Long.parseLong(hadoopMetrics.getMetricsValue("NumberOfMissingBlocks").toString()));

            String fsNameSystemStateUrl = String
                    .format(constant.JMXSERVERURLFORMAT, activeNameNodeUri, constant.FSNAMESYSTEMSTATE);
            HadoopMetrics fsNameSystemMetrics = client
                    .get(HadoopMetrics.class, fsNameSystemStateUrl, null, null);
            hdfsSummaryEntity
                    .setLiveDataNodeNums((int) fsNameSystemMetrics.getMetricsValue("NumLiveDataNodes"));
            hdfsSummaryEntity
                    .setDeadDataNodeNums((int) fsNameSystemMetrics.getMetricsValue("NumDeadDataNodes"));
            hdfsSummaryEntity
                    .setVolumeFailuresTotal((int) fsNameSystemMetrics.getMetricsValue("VolumeFailuresTotal"));
        } catch (Exception e) {
            logger.error(e.getMessage());
            hdfsSummaryEntity.setTrash(true);
        }
        hdfsSummaryEntity.setCreateTime((int) DateUtil.currentSeconds());
        hdfsSummaryEntity.setTrash(false);
        return hdfsSummaryEntity;
    }

    private List<QueueMetricsEntity> queryQueueMetrics() throws IOException {
        List<QueueMetricsEntity> queueMetricsEntities = new ArrayList<>();
        Date now = new Date();
        now.setSeconds(0);
        int timestamp = (int) (now.getTime() / 1000);

        List<String> rmUris = Arrays.asList(rmUriStr.split(";"));
        if (rmUris.isEmpty()) {
            return queueMetricsEntities;
        }

        String queueMetricsUrl = String.format(constant.JMXSERVERURLFORMAT, getActiveRmUri(rmUris), constant.QUEUEMETRICSALL);
        HadoopMetrics clusterMetrics = client
                .get(HadoopMetrics.class, queueMetricsUrl, null, null);
        List<Map<String, Object>> beans = clusterMetrics.getBeans();
        if (beans != null) {
            for (Map<String, Object> bean : beans) {
                QueueMetricsEntity qm = new QueueMetricsEntity();
                qm.setAppsPending((Integer) bean.get("AppsPending"));
                qm.setAppsRunning((Integer) bean.get("AppsRunning"));
                qm.setActiveUsers((Integer) bean.get("ActiveUsers"));
                qm.setAllocatedContainers((Integer) bean.get("AllocatedContainers"));
                qm.setAllocatedMb((Integer) bean.get("AllocatedMB"));
                qm.setAvailableMb((Integer) bean.get("AvailableMB"));
                qm.setReservedMb((Integer) bean.get("ReservedMB"));
                qm.setPendingContainers((Integer) bean.get("PendingContainers"));
                qm.setPendingMb((Integer) bean.get("PendingMB"));
                qm.setMetricsTime(timestamp);
                qm.setQueueName((String) bean.get("tag.Queue"));
                qm.setCreateTime(timestamp);
                queueMetricsEntities.add(qm);
            }
        }
        return queueMetricsEntities;
    }
}


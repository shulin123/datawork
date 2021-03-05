package com.shujuelin.datawork.common.entity.constant;

/**
 * @author : shujuelin
 * @date : 17:03 2021/2/25
 * 定义的系统常亮
 */
public class constant {

    public static final String JMXSERVERURLFORMAT = "http://%s/jmx?qry=%s";
    public static final String NAMENODEINFO = "Hadoop:service=NameNode,name=NameNodeInfo";
    public static final String FSNAMESYSTEM = "Hadoop:service=NameNode,name=FSNamesystem";
    public static final String FSNAMESYSTEMSTATE = "Hadoop:service=NameNode,name=FSNamesystemState";
    public static final String QUEUEMETRICS = "Hadoop:service=ResourceManager,name=QueueMetrics,q0=root";
    public static final String CLUSTERMETRICS = "Hadoop:service=ResourceManager,name=ClusterMetrics";

    public static final String QUEUEMETRICSALL = "Hadoop:service=ResourceManager,name=QueueMetrics,*";
}

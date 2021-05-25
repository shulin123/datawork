package com.shujuelin.datawork.common.entity.constant;

/**
 * @author : shujuelin
 * @date : 17:03 2021/2/25
 * 定义的hadoop的常量
 */
public class Constant {

    public static final String JMXSERVERURLFORMAT = "http://%s/jmx?qry=%s";
    public static final String NAMENODEINFO = "Hadoop:service=NameNode,name=NameNodeInfo";
    public static final String FSNAMESYSTEM = "Hadoop:service=NameNode,name=FSNamesystem";
    public static final String FSNAMESYSTEMSTATE = "Hadoop:service=NameNode,name=FSNamesystemState";
    public static final String QUEUEMETRICS = "Hadoop:service=ResourceManager,name=QueueMetrics,q0=root";
    public static final String CLUSTERMETRICS = "Hadoop:service=ResourceManager,name=ClusterMetrics";

    public static final String QUEUEMETRICSALL = "Hadoop:service=ResourceManager,name=QueueMetrics,*";

    //系统正常返回
    public static final int SYSTEM_SUCCESS = 200;
    //系统异常
    public static final int SYSTEM_EXCEPTION = 100;
    //系统输入参数异常
    public static final int ERROR_PARAM = 101;
    //用户不存在
    public static final int ERROR_USER_NOT_EXISTS = 102;
    //密码错误
    public static final int ERROR_PASSWORD = 103;
    //权限不足
    public static final int ERROR_PERMISSION = 104;

    /**
     * description : 远端请求的code和msg
     */
    public final static String REST_CODE        = "code";
    public final static String REST_MSG         = "msg";
    public final static String REST_RESULT      = "result";
}

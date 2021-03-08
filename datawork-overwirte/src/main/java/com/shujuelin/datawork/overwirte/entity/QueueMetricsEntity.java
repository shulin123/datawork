package com.shujuelin.datawork.overwirte.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shujuelin.datawork.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author : shujuelin
 * @date : 0:06 2021/2/10
 */

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("queue_metrics")
public class QueueMetricsEntity extends BaseEntity {

    /**
     * 自增id
     */
    @TableId
    private Integer id;
    //队列名称
    private String queueName;
    //队列中pending的数量
    private Integer appsPending;
    private Integer appsRunning;
    private Integer allocatedMb;
    private Integer availableMb;
    //队列中预留的内存资源
    private Integer reservedMb;
    private Integer pendingMb;
    private Integer allocatedContainers;
    private Integer pendingContainers;
    private Integer ActiveUsers;
    private Integer metricsTime;

}

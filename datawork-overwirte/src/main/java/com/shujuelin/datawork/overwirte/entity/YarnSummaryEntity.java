package com.shujuelin.datawork.overwirte.entity;

import com.baomidou.mybatisplus.annotation.TableId;

import com.baomidou.mybatisplus.annotation.TableName;
import com.shujuelin.datawork.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;



/**
 * @author : shujuelin
 * @date : 23:46 2021/2/9
 */

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("yarn_summary")
public class YarnSummaryEntity extends BaseEntity {

    @TableId
    private Integer id;

    /**
     * 活着的nodemanager
     */
    private Integer liveNodeManagerNums;

    /**
     * 死亡的nodemanager
     */
    private Integer deadNodeManagerNums;

    /**
     * 不健康的nodemanager
     */
    private Integer unhealthyNodeManagerNums;

    /**
     * 已经提交的app数量
     */
    private Integer submittedApps;

    /**
     * 运行的app数量
     */
    private Integer runningApps;

    /**
     * 完成的app数量
     */
    private Integer completedApps;

    /**
     * 杀死的app数量
     */
    private Integer killedApps;

    /**
     * 提交失败的应用数量
     */
    private Integer failedApps;

    /**
     * 分配的核数
     */
    private Integer allocatedCores;

    /**
     * 分配的容量
     */
    private Integer allocatedContainers;

    /**
     * 可利用的内存
     */
    private Long availableMem;

    /**
     * 可利用的核数
     */
    private Integer availableCores;

    /**
     * 待定的内存
     */
    private Long pendingMem;

    /**
     * 待定的cpu核数
     */
    private Integer pendingCores;

    /**
     * 待定的资源
     */
    private Integer pendingContainers;
    /**
     *待定的app数
     */
    private Integer pendingApps;

    /**
     * 恢复的内存
     */
    private Long reservedMem;

    /**
     * 恢复的核数
     */
    private Integer reservedCores;

    /**
     * 恢复的容量
     */
    private Integer reservedContainers;

    /**
     * 分配的内存
     */
    private Long allocatedMem;


}

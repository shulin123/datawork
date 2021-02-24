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
    private Long id;

    /**
     * 活着的nodemanager
     */
    private int liveNodeManagerNums;

    /**
     * 死亡的nodemanager
     */
    private int deadNodeManagerNums;

    /**
     * 不健康的nodemanager
     */
    private int unhealthyNodeManagerNums;

    /**
     * 已经提交的app数量
     */
    private int submittedApps;

    /**
     * 运行的app数量
     */
    private int runningApps;

    /**
     * 完成的app数量
     */
    private int completedApps;

    /**
     * 杀死的app数量
     */
    private int killedApps;

    /**
     * 提交失败的应用数量
     */
    private int failedApps;

    /**
     * 分配的核数
     */
    private int allocatedCores;

    /**
     * 分配的容量
     */
    private int allocatedContainers;

    /**
     * 可利用的内存
     */
    private Long availableMem;

    /**
     * 可利用的核数
     */
    private int availableCores;

    /**
     * 待定的内存
     */
    private Long pendingMem;

    /**
     * 待定的cpu核数
     */
    private int pendingCores;

    /**
     * 待定的资源
     */
    private int pendingContainers;
    /**
     *待定的app数
     */
    private int pendingApps;

    /**
     * 恢复的内存
     */
    private Long reservedMem;

    /**
     * 恢复的核数
     */
    private int reservedCores;

    /**
     * 恢复的容量
     */
    private int reservedContainers;

    /**
     * 分配的内存
     */
    private Long allocatedMem;


}

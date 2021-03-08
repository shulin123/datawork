package com.shujuelin.datawork.overwirte.entity;

import com.baomidou.mybatisplus.annotation.TableId;

import com.baomidou.mybatisplus.annotation.TableName;
import com.shujuelin.datawork.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author : shujuelin
 * @date : 22:32 2021/2/9
 * hdfs的概要
 */

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("hdfs_summary")
public class HdfsSummaryEntity extends BaseEntity {
   private static final long    serialVersionUID = 1L;
   /**
    * 自增id
    */
   @TableId
   private Integer id;

   /**
    * hdfs总容量
    */
   private Long total;

   /**
    * hdfs已经使用的容量
    */
   private Long dfsUsed;

   /**
    * hdfs使用百分比
    */
   private float percentUsed;

   /**
    * hdfs的空闲空间
    */
   private Long dfsFree;

   /**
    * 配置的dfs的空间有多少空间被不是hdfs的文件占用了的
    */
   private Long nonDfsUsed;

   /**
    * hdfs总的blocks数量
    */
   private Long totalBlocks;

   /**
    * hdfs总的文件数量
    */
   private Long totalFiles;

   /**
    * 丢失的blocks数量
    */
   private Long missingBlocks;

   /**
    * 活动的节点
    */
   private Integer liveDataNodeNums;

   /**
    * 死亡的节点
    */
   private Integer deadDataNodeNums;
   /**
    * 节点容量故障数
    */
   private Integer volumeFailuresTotal;
}

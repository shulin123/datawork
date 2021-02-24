package com.shujuelin.datawork.operator.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shujuelin.datawork.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : shujuelin
 * @date : 12:46 2021/2/17
 * 数据库的信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("db_info")
public class DbInfoEntity extends BaseEntity {

    private static final long    serialVersionUID = 1L;

    @TableId
    private Long id;
    private String name;
    private String detail;
    //属于哪个层级
    private String level;
    //在hdfs上的存储路径
    private String locationUri;
    //业务线的名称
    private String projectName;
    //项目id
    private Long projectId;
    private String admin;
    private String team;
}

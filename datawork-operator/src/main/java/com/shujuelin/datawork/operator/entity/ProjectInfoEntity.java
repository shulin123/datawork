package com.shujuelin.datawork.operator.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shujuelin.datawork.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : shujuelin
 * @date : 12:41 2021/2/17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("project_info")
public class ProjectInfoEntity extends BaseEntity {

    private static final long    serialVersionUID = 1L;
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //项目名
    private String name;
    //hdfs上的命名空间
    private String ns;
    //描述信息
    private String detail;
    //hdfs 目录
    private String basePath;
    //yarn 队列
    private String baseQueue;
    //空间配额
    private Long dsQuota;
    //文件数配额
    private Long nsQuota;
    //管理员
    private String admin;
    //所属团队
    private String team;

}

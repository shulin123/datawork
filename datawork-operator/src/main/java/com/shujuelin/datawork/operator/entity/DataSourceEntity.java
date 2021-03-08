package com.shujuelin.datawork.operator.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shujuelin.datawork.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.jdo.annotations.Column;

/**
 * @author : shujuelin
 * @date : 13:04 2021/2/17
 * 业务线的数据源
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("data_source")
public class DataSourceEntity extends BaseEntity {

    @TableId
    private Integer id;
    private String name;
    //数据源类型
    private SourceType sourceType;
    //连接信息(一个大的json)
    private String connectInfo;
    //项目名称
    private String projectName;
    //项目id
    private Long projectId;
    private String admin;
    private String team;
}

package com.shujuelin.datawork.taskmanager.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shujuelin.datawork.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.jdo.annotations.Column;
import java.util.List;


@Data
@TableName("plugin_package")
public class PluginPackage extends BaseEntity {

  @TableId(value = "id",type = IdType.AUTO)
  private Integer id;
  /**
   * 名称
   */
  private String name;
  /**
   * 版本
   */
  private String version;
  /**
   * 类型
   */
  private String pluginType;
  /**
   * 分类
   */
  private PluginCategory pluginCategory;
  /**
   * job类型
   */
  private String jobType;
  /**
   * 开发语言
   */
  private String lang;
  /**
   * 状态
   */
  private String admin;
  /**
   *
   */
  private String team;
  /**
   * 项目名称
   */
  private String projectName;
  /**
   * 项目id
   */
  private Integer projectId;
  /**
   * 插件描述
   */
  private String pluginDesc;
  /**
   * 默认参数
   */
  private List<PackageParam> defaultParams;
  /**
   * 输出参数
   */
  private List<PackageOutParam> outParams;
  /**
   * 插件状态
   */
  private PluginStatus pluginStatus;
  /**
   *
   */
  private String tags;
  /**
   * 插件程序的保存路径
   */
  private String pkgPath;

}

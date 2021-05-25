package com.shujuelin.datawork.taskmanager.entity;

import lombok.Data;

/**
 * 输出参数
 */
@Data
public class PackageOutParam {

  private String name;
  private ParamType type;
  private String description;
  private String defaultValue;
  private boolean required = false;
  private boolean userSetAble = false;
  private ParamEntityType entityType = ParamEntityType.PLAIN;
  private String example;

}

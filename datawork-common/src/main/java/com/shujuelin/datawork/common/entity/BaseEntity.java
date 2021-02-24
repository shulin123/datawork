package com.shujuelin.datawork.common.entity;

import lombok.Data;


import java.io.Serializable;

/**
 * @author : shujuelin
 * @date : 22:30 2021/2/9`
 */
@Data
public class BaseEntity implements Serializable {
    //是否是脏数据
    private boolean isTrash = false;
    //该条数据的插入时间
    private int createTime;
}

package com.shujuelin.datawork.common.entity;

import cn.hutool.core.date.DateTime;
import lombok.Data;


import java.io.Serializable;
import java.util.Date;

/**
 * @author : shujuelin
 * @date : 22:30 2021/2/9`
 */
@Data
public class BaseEntity implements Serializable {
    //是否是脏数据
    private boolean isTrash = false;
    //该条数据的插入时间
    private Date createTime;
}

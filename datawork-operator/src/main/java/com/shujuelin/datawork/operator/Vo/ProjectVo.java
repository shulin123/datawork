package com.shujuelin.datawork.operator.Vo;

import com.shujuelin.datawork.operator.entity.ProjectInfoEntity;
import lombok.Data;

import java.util.List;

/**
 * @author : shujuelin
 * @date : 22:32 2021/3/12
 */
@Data
public class ProjectVo {

    private Integer current;
    private Integer size;
    private Long total;
    private List<ProjectInfoEntity> userList;
}

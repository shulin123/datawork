package com.shujuelin.datawork.operator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shujuelin.datawork.operator.entity.DataSourceEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : shujuelin
 * @date : 13:11 2021/2/17
 */
@Mapper
public interface DataSourceDao extends BaseMapper<DataSourceEntity> {
}

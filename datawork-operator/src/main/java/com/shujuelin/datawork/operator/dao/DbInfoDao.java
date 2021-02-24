package com.shujuelin.datawork.operator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shujuelin.datawork.operator.entity.DbInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : shujuelin
 * @date : 13:10 2021/2/17
 */
@Mapper
public interface DbInfoDao extends BaseMapper<DbInfoEntity> {
}

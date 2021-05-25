package com.shujuelin.datawork.operator.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shujuelin.datawork.operator.Vo.ProjectVo;
import com.shujuelin.datawork.operator.entity.DataSourceEntity;
import com.shujuelin.datawork.operator.entity.DbInfoEntity;
import com.shujuelin.datawork.operator.entity.ProjectInfoEntity;

import java.io.IOException;
import java.util.List;

/**
 * @author : shujuelin
 * @date : 13:48 2021/2/17
 */
public interface MetaService {

    //创建项目ProjectInfoEntity
    Integer createProjectInfoEntity(ProjectInfoEntity projectInfo);

    //更新项目ProjectInfoEntity
    void updateProjectInfoEntity(ProjectInfoEntity projectInfo);

    //删除项目ProjectInfoEntity
    void delProjectInfoEntity(ProjectInfoEntity projectInfo);

    //查询ProjectInfoEntity详情
    ProjectInfoEntity findProjectInfoEntityById(long id);

    //查询查询ProjectInfoEntity详情 通过项目名称
    ProjectInfoEntity findProjectInfo(String projectName);


    //分页查询
    ProjectVo queryListProject(Integer current, Integer size);

    Integer createDbInfoEntity(DbInfoEntity dbInfoEntity);

    void updateDbInfoEntity(DbInfoEntity dbInfoEntity);

    void delDbInfoEntity(long id);

    DbInfoEntity findDbInfoEntityById(long id);

    DbInfoEntity findDbInfoEntityByName(String name);

    List<DbInfoEntity> findDbInfoEntityByProjectName(String name);

    List<DbInfoEntity> findDbInfoEntityByProjectId(long id);

    //Page<DbInfoEntity> listDbInfoEntitys(String team, int page, int size, String sort, Sort.Direction direction);

    List<String> showTables(String dbName);

    //拿到表的元数据信息
    Object getTableSchema(String dbName, String tableName);

    void createDataSource(DataSourceEntity dataSource);

    void updateDataSource(DataSourceEntity dataSource);

    void delDataSource(long id);

    DataSourceEntity findDataSourceById(long id);

    DataSourceEntity findDataSourceByName(String name);

    List<DataSourceEntity> findDataSourceByProjectName(String name);

    List<DataSourceEntity> findDataSourceByProjectId(long id);

   // Page<DataSourceEntity> listDataSources(String team, int page, int size, String sort, Sort.Direction direction);

}

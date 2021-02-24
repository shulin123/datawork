package com.shujuelin.datawork.operator.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    //创建ProjectInfoEntity
    void createProjectInfoEntity(ProjectInfoEntity projectInfoEntity) throws IOException, InterruptedException;

    //更新ProjectInfoEntity
    void updateProjectInfoEntity(ProjectInfoEntity projectInfoEntity) throws IOException, InterruptedException;

    //删除ProjectInfoEntity
    void delProjectInfoEntity(long id);

    //查询ProjectInfoEntity
    ProjectInfoEntity findProjectInfoEntityById(long id);

    ProjectInfoEntity findProjectInfoEntityByName(String name);

    List<String> listProjectNames(String team);

    //分页查询
    //Page<ProjectInfoEntity> listProjectInfoEntitys(String team, int page, int size, String sort, Sort.Direction direction);

    void createDbInfoEntity(DbInfoEntity dbInfoEntity) throws IOException, InterruptedException;

    void updateDbInfoEntity(DbInfoEntity dbInfoEntity);

    void delDbInfoEntity(long id);

    DbInfoEntity findDbInfoEntityById(long id);

    DbInfoEntity findDbInfoEntityByName(String name);

    List<DbInfoEntity> findDbInfoEntityByProjectName(String name);

    List<DbInfoEntity> findDbInfoEntityByProjectId(long id);

    //Page<DbInfoEntity> listDbInfoEntitys(String team, int page, int size, String sort, Sort.Direction direction);

    List<String> showTables(String dbName);

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

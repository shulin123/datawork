package com.shujuelin.datawork.operator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shujuelin.datawork.operator.dao.DataSourceDao;
import com.shujuelin.datawork.operator.dao.DbInfoDao;
import com.shujuelin.datawork.operator.dao.ProjectInfoDao;
import com.shujuelin.datawork.operator.entity.DataSourceEntity;
import com.shujuelin.datawork.operator.entity.DbInfoEntity;
import com.shujuelin.datawork.operator.entity.ProjectInfoEntity;
import com.shujuelin.datawork.operator.service.MetaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : shujuelin
 * @date : 11:32 2021/2/27
 */
@Service
public class MetaServiceImpl extends ServiceImpl<ProjectInfoDao, ProjectInfoEntity> implements MetaService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DbInfoDao dbInfoDao;
    @Autowired
    DataSourceDao dataSourceDao;

    /**
     * 创建项目
     * @param projectInfoEntity
     */
    @Override
    public void createProjectInfoEntity(ProjectInfoEntity projectInfoEntity) {
        //创建hdfs目录
        //设置hdfs配额
        //设置权限
        baseMapper.insert(projectInfoEntity);
    }

    /**
     * 更新项目
     * @param projectInfoEntity
     */
    @Override
    public void updateProjectInfoEntity(ProjectInfoEntity projectInfoEntity) {
        //设置hdfs配额
        baseMapper.updateById(projectInfoEntity);
    }

    /**
     * 删除项目
     * @param id
     */
    @Override
    public void delProjectInfoEntity(long id) {
      //并不是正真的删除，是设置为脏数据
        ProjectInfoEntity projectInfoEntity = baseMapper.selectById(id);
        projectInfoEntity.setTrash(true);
        baseMapper.insert(projectInfoEntity);
    }

    /**
     * 通过id查询项目
     * @param id
     * @return
     */
    @Override
    public ProjectInfoEntity findProjectInfoEntityById(long id) {
        ProjectInfoEntity projectInfoEntity = baseMapper.selectById(id);
        return projectInfoEntity;
    }

    /**
     * 通过名称查询
     * @param name
     * @return
     */
    @Override
    public ProjectInfoEntity findProjectInfoEntityByName(String name) {
        return null;
    }

    @Override
    public List<String> listProjectNames(String team) {
        return null;
    }

    /**
     * 新增db
     * @param dbInfoEntity
     */
    @Override
    public void createDbInfoEntity(DbInfoEntity dbInfoEntity) {
      //创建hive databases
        dbInfoDao.insert(dbInfoEntity);
    }

    /**
     * 更新db
     * @param dbInfoEntity
     */
    @Override
    public void updateDbInfoEntity(DbInfoEntity dbInfoEntity) {

        dbInfoDao.updateById(dbInfoEntity);
    }

    /**
     * 删除db
     * @param id
     */
    @Override
    public void delDbInfoEntity(long id) {

    }

    @Override
    public DbInfoEntity findDbInfoEntityById(long id) {
        return null;
    }

    @Override
    public DbInfoEntity findDbInfoEntityByName(String name) {
        return null;
    }

    @Override
    public List<DbInfoEntity> findDbInfoEntityByProjectName(String name) {
        return null;
    }

    @Override
    public List<DbInfoEntity> findDbInfoEntityByProjectId(long id) {
        return null;
    }

    @Override
    public List<String> showTables(String dbName) {
        return null;
    }

    @Override
    public Object getTableSchema(String dbName, String tableName) {
        return null;
    }

    @Override
    public void createDataSource(DataSourceEntity dataSource) {

    }

    @Override
    public void updateDataSource(DataSourceEntity dataSource) {

    }

    @Override
    public void delDataSource(long id) {

    }

    @Override
    public DataSourceEntity findDataSourceById(long id) {
        return null;
    }

    @Override
    public DataSourceEntity findDataSourceByName(String name) {
        return null;
    }

    @Override
    public List<DataSourceEntity> findDataSourceByProjectName(String name) {
        return null;
    }

    @Override
    public List<DataSourceEntity> findDataSourceByProjectId(long id) {
        return null;
    }
}

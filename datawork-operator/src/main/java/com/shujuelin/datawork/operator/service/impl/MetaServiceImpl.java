package com.shujuelin.datawork.operator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shujuelin.datawork.common.entity.exception.DataWorkException;
import com.shujuelin.datawork.common.entity.exception.ErrorCode;
import com.shujuelin.datawork.common.entity.utils.CommonCode;
import com.shujuelin.datawork.common.entity.utils.HadoopClient;
import com.shujuelin.datawork.common.entity.utils.R;
import com.shujuelin.datawork.operator.Vo.ProjectVo;
import com.shujuelin.datawork.operator.dao.DataSourceDao;
import com.shujuelin.datawork.operator.dao.DbInfoDao;
import com.shujuelin.datawork.operator.dao.ProjectInfoDao;
import com.shujuelin.datawork.operator.entity.DataSourceEntity;
import com.shujuelin.datawork.operator.entity.DbInfoEntity;
import com.shujuelin.datawork.operator.entity.ProjectInfoEntity;
import com.shujuelin.datawork.operator.service.MetaService;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.client.HdfsAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * @author : shujuelin
 * @date : 11:32 2021/2/27
 */
@Service
public class MetaServiceImpl extends ServiceImpl<ProjectInfoDao, ProjectInfoEntity> implements MetaService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${custom.hadoop.proxyuser}")
    private String proxyUser;
    @Value("${custom.hadoop.conf}")
    private String hadoopConfPath;
    @Value("${custom.hadoop.hivemetastore}")
    private String hiveMemetaStore;

    @Autowired
    DbInfoDao dbInfoDao;
    @Autowired
    DataSourceDao dataSourceDao;
    @Autowired
    MetaService metaService;

    /**
     * 创建项目
     * @param projectInfo
     */
    @Override
    public Integer  createProjectInfoEntity(ProjectInfoEntity projectInfo) {
        String projectName = projectInfo.getName();
        ProjectInfoEntity projectInfoEntity = findProjectInfo(projectName);
        if (projectInfoEntity != null){
            throw new DataWorkException("项目名称已经存在 !",CommonCode.SERVER_ERROR.code(),null);
        }
        //创建hdfs目录
        String hdfsUri = String.format("hdfs://%s", projectInfo.getNs());
        HadoopClient hadoopClient = new HadoopClient(proxyUser, hadoopConfPath, hiveMemetaStore);
        try {
            FileSystem fileSystem = hadoopClient.getFileSystem(null, hdfsUri);
            if (!fileSystem.exists(new Path(projectInfo.getBasePath()))) {
                fileSystem.mkdirs(new Path(projectInfo.getBasePath()));
            }
            //设置hdfs配额
            HdfsAdmin hdfsAdmin = hadoopClient.getHdfsAdmin(hdfsUri);
            hdfsAdmin.setQuota(new Path(projectInfo.getBasePath()), projectInfo.getDsQuota());
            hdfsAdmin.setSpaceQuota(new Path(projectInfo.getBasePath()), projectInfo.getNsQuota());
            //设置权限
            baseMapper.insert(projectInfo);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("创建项目信息失败 :" + e.getMessage());
            throw new DataWorkException("项目表插入失败，数据库操作异常", CommonCode.SERVER_ERROR.code(),null);
        }
        return projectInfo.getId();
    }



    /**
     * 更新项目
     * @param projectInfo
     */
    @Override
    public void updateProjectInfoEntity(ProjectInfoEntity projectInfo) {

        try{
            //设置hdfs配额
            String hdfsUri = String.format("hdfs://%s", projectInfo.getNs());
            HadoopClient hadoopClient = new HadoopClient(proxyUser, hadoopConfPath, hiveMemetaStore);
            HdfsAdmin hdfsAdmin = hadoopClient.getHdfsAdmin(hdfsUri);
            hdfsAdmin.setQuota(new Path(projectInfo.getBasePath()), projectInfo.getDsQuota());
            hdfsAdmin.setSpaceQuota(new Path(projectInfo.getBasePath()), projectInfo.getNsQuota());
            ProjectInfoEntity entity = new ProjectInfoEntity();
            entity.setId(projectInfo.getId());
            entity.setAdmin(projectInfo.getAdmin());
            baseMapper.updateById(entity);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("更新项目信息失败 :" + e.getMessage());
            throw  new DataWorkException("项目表更新失败，数据库操作异常", CommonCode.SERVER_ERROR.code(),null);
        }
    }

    /**
     * 删除项目
     * @param projectInfo
     */
    @Override
    public void delProjectInfoEntity(ProjectInfoEntity projectInfo) {
      //并不是正真的删除，是设置为脏数据
        ProjectInfoEntity projectInfoEntity = baseMapper.selectById(projectInfo.getId());
        projectInfoEntity.setTrash(true);
        try {
            baseMapper.updateById(projectInfoEntity);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("删除项目信息失败 :" + e.getMessage());
            throw  new DataWorkException("删除项目信息失败，数据库操作异常", CommonCode.SERVER_ERROR.code(),null);
        }

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
     * 根据项目名称查询
     * @param projectName
     * @return
     */
    @Override
    public ProjectInfoEntity findProjectInfo(String projectName) {
        QueryWrapper<ProjectInfoEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("name",projectName);
        ProjectInfoEntity projectInfoEntity = baseMapper.selectOne(wrapper);
        return projectInfoEntity;
    }


    /**
     * 分页查询
     *参数一是当前页，参数二是每页个数
     * @param current
     * @param size
     * @return
     */
    @Override
    public ProjectVo queryListProject(Integer current, Integer size) {

        ProjectVo projectVo = new ProjectVo();
        IPage<ProjectInfoEntity> page = new Page<>(current, size);
        baseMapper.selectPage(page, null);
        projectVo.setCurrent(current);
        projectVo.setSize(size);
        projectVo.setTotal(page.getTotal());
        projectVo.setUserList(page.getRecords());
        return projectVo;

    }

    /**
     * 新增db
     * @param dbInfoEntity
     */
    @Override
    public Integer createDbInfoEntity(DbInfoEntity dbInfoEntity) {

        String dbName = dbInfoEntity.getName();
        DbInfoEntity dbInfo = findDbInfoEntityByName(dbName);
        if (dbInfo != null){
            throw new DataWorkException("数据库名已经存在 ！",ErrorCode.ERROR_PARAM,null);
        }
        HadoopClient hadoopClient = new HadoopClient(proxyUser, hadoopConfPath, hiveMemetaStore);
        String projectName = dbInfoEntity.getProjectName();
        if (projectName == null){
            throw new DataWorkException("项目不存在 ！",ErrorCode.ERROR_PARAM,null);
        }
        ProjectInfoEntity projectInfoEntity = metaService.findProjectInfo(projectName);
        dbInfoEntity.setProjectId(projectInfoEntity.getId());
        dbInfoEntity.setLocationUri(projectInfoEntity.getBasePath() + "/warehouse/" +
                dbInfoEntity.getLevel() + "/" + dbInfoEntity.getName() + ".db");
        try {
            dbInfoDao.insert(dbInfoEntity);
            //创建hive的数据库
            hadoopClient.createDataBase(dbInfoEntity.getName(),dbInfoEntity.getLocationUri(),dbInfoEntity.getDetail()
                    ,dbInfoEntity.getTeam());
        }catch (Exception e){
            e.printStackTrace();
        }
        return dbInfoEntity.getId();
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
        dbInfoDao.deleteById(id);
    }

    @Override
    public DbInfoEntity findDbInfoEntityById(long id) {
        return null;
    }

    /**
     * 根据名称查询hive数据库信息
     * @param name
     * @return
     */
    @Override
    public DbInfoEntity findDbInfoEntityByName(String name) {
        if (name == null){
            return null;
        }
        QueryWrapper<DbInfoEntity> entityQueryWrapper = new QueryWrapper<>();
        entityQueryWrapper.eq("name",name);
        DbInfoEntity dbInfoEntity = dbInfoDao.selectOne(entityQueryWrapper);
        return dbInfoEntity;
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
        HadoopClient hadoopClient = new HadoopClient(proxyUser, hadoopConfPath, hiveMemetaStore);

        return hadoopClient.showTables(dbName);
    }

    @Override
    public Object getTableSchema(String dbName, String tableName) {
        HadoopClient hadoopClient = new HadoopClient(proxyUser, hadoopConfPath, hiveMemetaStore);

        return hadoopClient.getTableSchemas(dbName, tableName);
    }

    @Override
    public void createDataSource(DataSourceEntity dataSource) {

         dataSourceDao.insert(dataSource);
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

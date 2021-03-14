package com.shujuelin.datawork.common.entity.utils;


import com.google.common.base.Strings;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.client.HdfsAdmin;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.HiveMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.hadoop.hive.metastore.api.FieldSchema;
import org.apache.hadoop.hive.metastore.api.PrincipalType;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.net.URI;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : shujuelin
 * @date : 15:26 2021/3/6
 */
public class HadoopClient {

    //hadoop一些相关的配置
    private Configuration conf;
    //hive元数据的地址
    private String hiveMetaStoreUris;
    //用户组信息：保存hadoop用户及组信息
    private UserGroupInformation ugi;
    //代理用户
    private String proxyUser;

    public HadoopClient(String proxyUser, String hadoopConfPath, String hiveMetaStoreUris) {
        this.proxyUser = proxyUser;
        this.hiveMetaStoreUris = hiveMetaStoreUris;
        ugi = UserGroupInformation.createRemoteUser(proxyUser);
        this.conf = new Configuration();
        conf.addResource(new Path(String.format("%s/hdfs-site.xml", hadoopConfPath)));
        conf.addResource(new Path(String.format("%s/core-site.xml", hadoopConfPath)));

    }

    /**
     * 泛型的方法
     * @param action
     * @param realUser
     * @param <T>
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public <T> T doPrivileged(PrivilegedExceptionAction<T> action, String realUser) throws IOException, InterruptedException {
        if (Strings.isNullOrEmpty(realUser)) {
            return ugi.doAs(action);
        }
        //创建proxyUser用户
        UserGroupInformation proxyUser = UserGroupInformation.createProxyUser(realUser, ugi);
        //访问集群时通过proxyUser.doAs方式进行调用
        return proxyUser.doAs(action);
    }

    /**
     * 获取hdfs的文件系统
     * 采用匿名内部类
     * @param realUser
     * @param hdfsUri
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public FileSystem getFileSystem(String realUser, String hdfsUri) throws IOException, InterruptedException {
        return doPrivileged(
                //直接实例化接口对象
                new PrivilegedExceptionAction<FileSystem>() {
                    @Override
                    public FileSystem run() throws Exception {
                        return FileSystem.newInstance(URI.create(hdfsUri), conf);
                    }
                }, realUser);
    }

    /**
     * 获取hdfs的Admin
     * @param hdfsUri
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public HdfsAdmin getHdfsAdmin(String hdfsUri) throws IOException, InterruptedException {
        return doPrivileged(
                new PrivilegedExceptionAction<HdfsAdmin>() {
                    @Override
                    public HdfsAdmin run() throws Exception {
                        return new HdfsAdmin(URI.create(hdfsUri), conf);
                    }
                }, null);
    }

    /**
     * hive创建数据库
     * @param name
     * @param dbPath
     * @param desc
     * @param realUser
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public Object createDataBase(String name,String dbPath,String desc,String realUser) throws IOException, InterruptedException {
        return doPrivileged(new PrivilegedExceptionAction<Object>() {
            @Override
            public Object run() throws Exception {
                HiveConf hiveConf = new HiveConf();
                hiveConf.setIntVar(HiveConf.ConfVars.METASTORETHRIFTCONNECTIONRETRIES,3);
                hiveConf.setVar(HiveConf.ConfVars.METASTOREURIS,hiveMetaStoreUris);
                HiveMetaStoreClient hiveMetaStoreClient = null;
                try{
                    hiveMetaStoreClient = new HiveMetaStoreClient(hiveConf);
                    Database db = new Database();
                    db.setName(name);
                    db.setDescription(desc);
                    db.setOwnerName(realUser);
                    db.setOwnerType(PrincipalType.USER);
                    db.setLocationUri(dbPath);
                    hiveMetaStoreClient.createDatabase(db);
                }catch (Exception e){
                    throw new RuntimeException(e.getMessage());
                }finally {
                    if(hiveMetaStoreClient!=null){
                        hiveMetaStoreClient.close();
                    }
                }
                return null;
            }
        },realUser);
    }

    /**
     * 获取hive数据库里的所有表
     * @param dbName
     * @return
     */
    public List<String> showTables(String dbName){
        HiveMetaStoreClient hiveMetaStoreClient = null;
        try{
            HiveConf hiveConf = new HiveConf();
            hiveConf.setIntVar(HiveConf.ConfVars.METASTORETHRIFTCONNECTIONRETRIES,3);
            hiveConf.setVar(HiveConf.ConfVars.METASTOREURIS,hiveMetaStoreUris);
            hiveMetaStoreClient=new HiveMetaStoreClient(hiveConf);
            return hiveMetaStoreClient.getAllTables(dbName);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }finally {
            if(hiveMetaStoreClient!=null){
                hiveMetaStoreClient.close();
            }
        }
    }

    /**
     * 获取hive数据库里某张表的元数据
     * @param dbName
     * @param tableName
     * @return
     */
    public List<Map<String,String>> getTableSchemas(String dbName, String tableName){
        HiveMetaStoreClient hiveMetaStoreClient = null;
        try{
            HiveConf hiveConf = new HiveConf();
            hiveConf.setIntVar(HiveConf.ConfVars.METASTORETHRIFTCONNECTIONRETRIES,3);
            hiveConf.setVar(HiveConf.ConfVars.METASTOREURIS,hiveMetaStoreUris);
            hiveMetaStoreClient=new HiveMetaStoreClient(hiveConf);

            List<FieldSchema> schema = hiveMetaStoreClient.getSchema(dbName, tableName);
            //java8的流操作
            return schema.stream().map(col->{
                Map<String,String> colInfo=new HashMap<>();
                colInfo.put("name",col.getName());
                colInfo.put("type",col.getType());
                colInfo.put("comment",col.getComment());
                return colInfo;
            }).collect(Collectors.toList());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }finally {
            if(hiveMetaStoreClient!=null){
                hiveMetaStoreClient.close();
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        HadoopClient hadoopClient = new HadoopClient("hadoop","/home/jixin/imooc_3/naga/naga-server/src/main/resources","thrift://47.108.140.82:9083");

        FileSystem fileSystem = hadoopClient.getFileSystem(null, "hdfs://47.108.140.82:9000");
        FileStatus[] fileStatuses = fileSystem.listStatus(new Path("/"));
        Arrays.stream(fileStatuses).forEach(fileStatus -> {
            System.out.println(fileStatus.getPath().getName());
        });


        System.out.println("--------------------------");

        List<String> db01 = hadoopClient.showTables("db01");
        System.out.println(db01);

        List<Map<String, String>> tableSchemas = hadoopClient.getTableSchemas("db01", "log_dev1");
        System.out.println(tableSchemas);


    }

}

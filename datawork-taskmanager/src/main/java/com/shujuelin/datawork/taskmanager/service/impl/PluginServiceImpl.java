package com.shujuelin.datawork.taskmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Joiner;
import com.shujuelin.datawork.common.entity.constant.Constant;
import com.shujuelin.datawork.common.entity.exception.DataWorkException;
import com.shujuelin.datawork.common.entity.utils.FileUtil;
import com.shujuelin.datawork.taskmanager.dao.PluginPackageDao;
import com.shujuelin.datawork.taskmanager.entity.PluginCategory;
import com.shujuelin.datawork.taskmanager.entity.PluginPackage;
import com.shujuelin.datawork.taskmanager.plugin.CategoryGroup;
import com.shujuelin.datawork.taskmanager.plugin.PluginPackageParser;
import com.shujuelin.datawork.taskmanager.service.PluginService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PluginServiceImpl extends ServiceImpl<PluginPackageDao,PluginPackage> implements PluginService {

    @Value("${custom.task.packageDir}")
    private String packageDir;

    @Autowired
    PluginPackageDao pluginPackageDao;

    @PostConstruct
    public void init() {
        File file = new File(packageDir);
        if (file.exists() && file.isFile()) {
            throw new DataWorkException("packagedir is a file", Constant.SYSTEM_EXCEPTION,null);
        }
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * /保持plugin file, 对zip 进行解压缩， 解析meta.json 将plugin信息保持到数据库，将插件保存到本地
     * @param inputStream
     * @param pkgName
     * @param pkgVersion
     * @return
     * @throws IOException
     */
    @Override
    public PluginPackage savePluginFile(InputStream inputStream, String pkgName, String pkgVersion) throws IOException {
        //保持inputstream到tmp目录
        String storeDir = Joiner.on(File.separator).join(this.packageDir, "tmp", pkgName, pkgVersion);
        String pluginDir = Joiner.on(File.separator).join(this.packageDir, pkgName);
        File dir = new File(storeDir);
        if (dir.exists()) {
            FileUtil.deleteFileOrDir(dir);
        }
        dir.mkdirs();
        File file = new File(storeDir + File.separator + pkgName + "-" + pkgVersion + ".zip");
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        IOUtils.copy(inputStream, fileOutputStream);
        fileOutputStream.close();
        inputStream.close();
        //调用parser方法 生成plugin package实体类
        PluginPackage pluginPackage = PluginPackageParser.parse(file, storeDir);
        file.delete();
        //将插件程序 移动到plugin dir   插件名字和版本名字一致
        if (pluginPackage.getName().equals(pkgName) && pluginPackage.getVersion().equals(pkgVersion)) {
            FileUtil.copyFileOrDirectory(dir, pluginDir, pkgVersion);
            pluginPackage.setPkgPath(pluginDir + File.separator + pkgVersion);
        } else {
            throw new DataWorkException("pkg name or version is error", Constant.ERROR_PARAM,null);
        }
        FileUtil.deleteFileOrDir(dir);
        return pluginPackage;
    }

    /**
     * 获取plugin列表
     * @param current
     * @param size
     * @return
     */
    @Override
    public PluginPackage getPlugins(Integer current, Integer size) {
        return null;
    }

    @Override
    public PluginPackage getPluginByNameAndVersion(String name, String version) {
        /*return pluginPackageRepository
                .findByNameAndVersion(name, version);*/
        return null;
    }


    @Override
    public Object getPluginGroupBy() {
       /* PluginPackage pluginPackage = new PluginPackage();
        pluginPackage.setTrash(false);
        List<PluginPackage> all = pluginPackageDao.sele(Example.of(pluginPackage));
        Map<PluginCategory, List<PluginPackage>> pluginCategoryListMap = all.stream().collect(Collectors.groupingBy(PluginPackage::getPluginCategory));
        List<CategoryGroup> result = new ArrayList<>();
        Integer id = 1;
        for (PluginCategory category: pluginCategoryListMap.keySet()){
            CategoryGroup categoryGroup = new CategoryGroup();
            categoryGroup.setName(category.name());
            categoryGroup.setId(id.toString());
            List<CategoryGroup.PluginMeta> pluginMetas = pluginCategoryListMap.get(category).stream().map(pluginPackage1 -> {
                CategoryGroup.PluginMeta pluginMeta = new CategoryGroup.PluginMeta();
                pluginMeta.setId(pluginPackage1.getId().toString());
                pluginMeta.setName(String.format("%s-%s", pluginPackage1.getName(), pluginPackage1.getVersion()));
                pluginMeta.setType(String.format("%s-%s", pluginPackage1.getName(), pluginPackage1.getVersion()));
                return pluginMeta;
            }).collect(Collectors.toList());
            categoryGroup.setChildren(pluginMetas);
            result.add(categoryGroup);
            id++;
        }
        return result;*/
       return null;
    }

    @Override
    public PluginPackage getPlugin(long id) {
        //return pluginPackageDao.findById(id).get();
        return null;
    }

    @Override
    public void delPlugin(long id) throws IOException {
        /*PluginPackage pluginPackage = pluginPackageDao.findById(id).get();
        removePluginFile(pluginPackage.getName(), pluginPackage.getVersion());
        pluginPackageDao.delete(pluginPackage);*/

    }

    @Override
    public void delPlugin(String name, String version) throws IOException {
        /*PluginPackage pluginPackage = pluginPackageDao.findByNameAndVersion(name, version);
        removePluginFile(name, version);
        pluginPackageDao.delete(pluginPackage);*/
    }

    @Override
    public List<PluginPackage> getPluginsByName(String name) {
        //return pluginPackageDao.findByName(name);
        return null;
    }

    @Override
    public void update(PluginPackage pluginPackage) {

       pluginPackageDao.insert(pluginPackage);
    }

    private void removePluginFile(String name, String version) throws IOException {
        FileUtil.deleteFileOrDir(new File(Joiner.on(File.separator).join(packageDir, name, version)));
    }

    @Override
    public String getPackageStoreLocation() {
        return this.packageDir;
    }

}

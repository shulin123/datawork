package com.shujuelin.datawork.taskmanager.plugin;


import com.shujuelin.datawork.common.entity.constant.Constant;
import com.shujuelin.datawork.common.entity.exception.DataWorkException;
import com.shujuelin.datawork.common.entity.utils.FileUtil;
import com.shujuelin.datawork.common.entity.utils.JsonUtil;
import com.shujuelin.datawork.taskmanager.entity.PackageParam;
import com.shujuelin.datawork.taskmanager.entity.PluginPackage;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.zip.ZipFile;

/**
 * 解析meta.json
 */
public class PluginPackageParser {


    //解压zip包
    public static PluginPackage parse(File zipFile, String packageDir) throws IOException {
        //将zip包 解压到packageDir
        FileUtil.unzip(new ZipFile(zipFile),new File(packageDir));

        //获取插件的meta.json  并解析   File.separator文件分隔符
        File file = new File(packageDir + File.separator + "meta.json");
        if(!file.exists()){
            throw new DataWorkException("meta.json not exists", Constant.ERROR_PARAM,null);
        }
        PackageMetaInfo packageMetaInfo = parseMetaInfo(file);
        //根据meta json文件 生成plguinpackage并返回
        PluginPackage pluginPackage = new PluginPackage();
        pluginPackage.setName(packageMetaInfo.getName());
        pluginPackage.setName(packageMetaInfo.getName());
        pluginPackage.setVersion(packageMetaInfo.getVersion());
        pluginPackage.setJobType(packageMetaInfo.getJobType());
        pluginPackage.setLang(packageMetaInfo.getLanguage());
        pluginPackage.setOutParams(packageMetaInfo.getOutParams());
        List<PackageParam> paramSchemas =
                JobTypeParamSchemas.getJobTypeParamSchemas(pluginPackage.getJobType(),
                        pluginPackage.getLang());

        if (packageMetaInfo.getPkgParams() != null && packageMetaInfo.getPkgParams().size() > 0) {
            paramSchemas.addAll(packageMetaInfo.getPkgParams());
        }
        pluginPackage.setDefaultParams(paramSchemas);
        return pluginPackage;
    }

    //把meta.json转为实体
    public static PackageMetaInfo parseMetaInfo(File metaFile) throws IOException {
        byte[] bytes = Files.readAllBytes(metaFile.toPath());
        String json = new String(bytes, "UTF-8");
        return JsonUtil.fromJson(PackageMetaInfo.class, json);
    }

}

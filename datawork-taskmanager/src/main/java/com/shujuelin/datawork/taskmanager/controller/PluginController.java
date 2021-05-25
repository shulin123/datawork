package com.shujuelin.datawork.taskmanager.controller;

import cn.hutool.core.date.DateUtil;
import com.shujuelin.datawork.common.entity.constant.Constant;
import com.shujuelin.datawork.common.entity.exception.DataWorkException;
import com.shujuelin.datawork.common.entity.utils.R;
import com.shujuelin.datawork.common.entity.utils.ResultUtils;
import com.shujuelin.datawork.taskmanager.entity.PluginCategory;
import com.shujuelin.datawork.taskmanager.entity.PluginPackage;
import com.shujuelin.datawork.taskmanager.entity.PluginStatus;
import com.shujuelin.datawork.taskmanager.entity.ProjectInfoEntity;
import com.shujuelin.datawork.taskmanager.feign.ProjectRemoteService;
import com.shujuelin.datawork.taskmanager.service.PluginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

/**
 * @author : shujuelin
 * @date : 9:56 2021/4/21
 */
@Api("任务线模型")
@RestController
@RequestMapping("datawork/v1/task")
//跨域
@CrossOrigin
public class PluginController {

    @Autowired
    PluginService pluginService;
    @Autowired
    ProjectRemoteService projectRemoteService;

    @ResponseBody
    @PostMapping("plugin")
    @ApiOperation("上传插件")
    @CrossOrigin(value = "*",allowedHeaders="*",allowCredentials="true",maxAge = 10000)
    public Object uploadPlugin(@RequestParam("pkgName") String pkgName,
                               @RequestParam("pkgVersion") String pkgVersion,
                               @RequestParam(value = "status", defaultValue = "Dev") PluginStatus status,
                               @RequestParam(value = "description", defaultValue = "") String description,
                               @RequestParam(value = "tags", defaultValue = "") String tags,
                               @RequestParam("category") PluginCategory category,
                               @RequestParam("projectName") String projectName,
                               @RequestParam("file") MultipartFile file) {

        R projectInfoByName = projectRemoteService.findProjectByName(projectName);

        if (projectInfoByName == null) {
            return R.error(Constant.ERROR_PARAM, "project not exists");
        }
        //调用save pluginfile方法
        try {
            ProjectInfoEntity projectInfo = ResultUtils.getObjectFromResult(projectInfoByName, ProjectInfoEntity.class);
            PluginPackage pluginPackage = pluginService.savePluginFile(file.getInputStream(), pkgName, pkgVersion);
            pluginPackage.setPluginStatus(status);
            pluginPackage.setPluginDesc(description);
            pluginPackage.setTags(tags);
            pluginPackage.setPluginCategory(category);
            pluginPackage.setProjectId(projectInfo.getId());
            pluginPackage.setProjectName(projectName);
            //todo set admin and team by loginuser
            pluginPackage.setAdmin("shujuelin");
            pluginPackage.setTeam("bigdata");
            pluginPackage.setTrash(false);
            pluginPackage.setCreateTime(new Date());
            pluginService.update(pluginPackage);
            return R.ok();
        } catch (IOException e) {
            e.printStackTrace();
            throw new DataWorkException(e.getMessage(),Constant.ERROR_PARAM,null);
        }

        //完善plugin 详情

    }
}

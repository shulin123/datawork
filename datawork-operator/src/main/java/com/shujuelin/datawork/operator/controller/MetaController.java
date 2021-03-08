package com.shujuelin.datawork.operator.controller;

import com.shujuelin.datawork.common.entity.utils.R;
import com.shujuelin.datawork.operator.entity.ProjectInfoEntity;
import com.shujuelin.datawork.operator.service.MetaService;
import com.shujuelin.datawork.operator.service.impl.MetaServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : shujuelin
 * @date : 17:08 2021/3/6
 */
@RestController
@RequestMapping("datawork/v1/Meta")
//跨域
@CrossOrigin
public class MetaController {

    private static Logger log = LoggerFactory.getLogger(MetaController.class);
    @Autowired
    MetaService metaService;

    //列出projectInfo (分页)

    //创建projectInfo
    @PostMapping(value = "/project/add")
    public R addProject(@RequestBody ProjectInfoEntity projectInfo){

        log.info("This interface addProject Begin: ");
        // 验证权限
        long startTime = System.currentTimeMillis();
        int uuid = metaService.createProjectInfoEntity(projectInfo);
        long endTime=System.currentTimeMillis();
        log.info("This interface addProject End: executeDeploy cost = "+(endTime-startTime)+"ms");
        return R.ok().put(uuid);
    }
    //列出dbInfo (分页)
    //创建dbInfo
}

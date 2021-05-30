package com.shujuelin.datawork.operator.controller;

import com.shujuelin.datawork.common.entity.utils.R;
import com.shujuelin.datawork.operator.Vo.ProjectVo;
import com.shujuelin.datawork.operator.entity.DbInfoEntity;
import com.shujuelin.datawork.operator.entity.ProjectInfoEntity;
import com.shujuelin.datawork.operator.service.MetaService;
import com.shujuelin.datawork.operator.service.impl.MetaServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : shujuelin
 * @date : 17:08 2021/3/6
 */
@Api("数据业务")
@RestController
@RequestMapping("datawork/v1/Meta")
//跨域
@CrossOrigin
public class MetaController {

    private static Logger log = LoggerFactory.getLogger(MetaController.class);
    @Autowired
    MetaService metaService;


    /**
     * 创建projectInfo
     * @param projectInfo
     * @return
     */
    @ApiOperation("创建项目")
    @PostMapping(value = "/project/add")
    public R addProject(@RequestBody ProjectInfoEntity projectInfo){

        log.info("This interface addProject Begin: ");
        long startTime = System.currentTimeMillis();
        int uuid = metaService.createProjectInfoEntity(projectInfo);
        long endTime=System.currentTimeMillis();
        log.info("This interface addProject End: executeDeploy cost = "+(endTime-startTime)+"ms");
        return R.ok().put(uuid);
    }

    /**
     * 更新project项目
     * @param projectInfo
     * @return
     */
    @ApiOperation("更新项目")
    @PostMapping(value = "/project/update")
   public R updateProject(@RequestBody ProjectInfoEntity projectInfo){

       log.info("This interface updateProject Begin: ");
       long startTime = System.currentTimeMillis();
       metaService.updateProjectInfoEntity(projectInfo);
       long endTime = System.currentTimeMillis();
       log.info("This interface updateProject End: executeDeploy cost = "+(endTime-startTime)+"ms");
       return R.ok();
   }

    /**
     * 删除project项目
     * @param projectInfo
     * @return
     */
   @ApiOperation("删除项目")
   @PostMapping(value = "/project/delete")
   public R delProject(@RequestBody ProjectInfoEntity projectInfo){
       log.info("This interface delProject Begin: ");
       long startTime = System.currentTimeMillis();
       metaService.delProjectInfoEntity(projectInfo);
       long endTime = System.currentTimeMillis();
       log.info("This interface updateProject End: executeDeploy cost = "+(endTime-startTime)+"ms");
       return R.ok();
   }

    /**
     * 通过id进行查询
     * @param id
     * @return
     */
   @ApiOperation("项目查询")
   @GetMapping(value = "/project/query")
   public R findProject(@RequestParam("id") int id){
       log.info("This interface delProject Begin: ");
       long startTime = System.currentTimeMillis();
       ProjectInfoEntity projectInfo = metaService.findProjectInfoEntityById(id);
       long endTime = System.currentTimeMillis();
       log.info("This interface updateProject End: executeDeploy cost = "+(endTime-startTime)+"ms");
       return R.ok().put(projectInfo);
   }

   @ApiOperation("通过项目名称查询project")
   @GetMapping(value = "/project/querybyname")
   public R findProjectByName(@RequestParam("projectName") String projectName){
       log.info("This interface delProject Begin: ");
       long startTime = System.currentTimeMillis();
       ProjectInfoEntity projectInfo = metaService.findProjectInfo(projectName);
       long endTime = System.currentTimeMillis();
       log.info("This interface updateProject End: executeDeploy cost = "+(endTime-startTime)+"ms");
       return R.ok().put(projectInfo);
   }


   @ApiOperation("项目分页查询")
   @GetMapping(value = "/project/list")
   public R listProject(@RequestParam("current") Integer current,@RequestParam("size") Integer size){
       log.info("This interface listProject Begin: ");
       long startTime = System.currentTimeMillis();
       ProjectVo projectVo = metaService.queryListProject(current,size);
       long endTime = System.currentTimeMillis();
       log.info("This interface updateProject End: executeDeploy cost = "+(endTime-startTime)+"ms");
       return R.ok().put(projectVo);
   }

    //创建dbInfo

    @ApiOperation("创建db数据库")
    @PostMapping(value = "/db/add")
    public R createDb(@RequestBody DbInfoEntity dbInfoEntity){
        log.info("This interface DbInfoEntity Begin: ");
        int uuid = metaService.createDbInfoEntity(dbInfoEntity);
        return R.ok().put(uuid);
    }
}

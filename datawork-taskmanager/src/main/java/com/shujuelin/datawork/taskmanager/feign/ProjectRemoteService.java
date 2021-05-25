package com.shujuelin.datawork.taskmanager.feign;

import com.shujuelin.datawork.common.entity.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author : shujuelin
 * @date : 10:26 2021/4/21
 * 远程调用
 */
@FeignClient("datawork-operator")
//泛指各种组件，就是说当我们的类不属于各种归类的时候（不属于@Controller、@Services等的时候），
// 我们就可以使用@Component来标注这个类。
@Component
public interface ProjectRemoteService {
    @GetMapping(value = "datawork/v1/Meta/project/querybyname")
    R findProjectByName(String projectName);
}

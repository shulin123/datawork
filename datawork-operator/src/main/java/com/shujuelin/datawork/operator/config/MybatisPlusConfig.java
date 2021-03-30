package com.shujuelin.datawork.operator.config;

/**
 * @author : shujuelin
 * @date : 17:41 2021/3/12
 */

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分页查询
 */
@Configuration
@ConditionalOnClass(value = {PaginationInterceptor.class})
public class MybatisPlusConfig {

    @Bean
      public PaginationInterceptor paginationInterceptor() {
                 PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
                return paginationInterceptor;
            }
}

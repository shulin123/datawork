package com.shujuelin.datawork.overwirte.schedule;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : shujuelin
 * @date : 17:51 2021/2/10
 */

/**
 * jms的beans封装
 */
@Getter
@Setter
public class HadoopMetrics {

    List<Map<String,Object>> beans = new ArrayList<>();

    public Object getMetricsValue(String name){

        if (beans.isEmpty()){

            return null;
        }
        //当Map集合中有这个key时，就使用这个key值；
        //  如果没有就使用默认值defaultValue。
        return beans.get(0).getOrDefault(name,null);
    }
}

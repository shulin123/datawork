package com.shujuelin.datawork.taskmanager.plugin;


import com.shujuelin.datawork.taskmanager.entity.PackageOutParam;
import com.shujuelin.datawork.taskmanager.entity.PackageParam;
import lombok.Data;

import java.util.List;

/**
 * meta.json的实体对象
 */
@Data
public class PackageMetaInfo {
    private String name;
    private String version;
    private String jobType;
    private String language;
    private List<PackageParam> pkgParams;
    private List<PackageOutParam> outParams;
}

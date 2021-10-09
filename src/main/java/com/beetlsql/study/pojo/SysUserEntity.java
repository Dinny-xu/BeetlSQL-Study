package com.beetlsql.study.pojo;

import lombok.Data;
import org.beetl.sql.annotation.entity.AutoID;
import org.beetl.sql.annotation.entity.Table;

@Data
@Table(name = "sys_user")
public class SysUserEntity {

    @AutoID
    private Integer id;
    private String name;
    //@Column("dept_id") NameConversion --> 可以指定列名
    private Integer departmentId;
}
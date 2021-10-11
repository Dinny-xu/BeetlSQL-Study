package com.beetlsql.study.pojo;

import lombok.Data;
import org.beetl.sql.annotation.entity.Table;

import java.util.Date;

@Data
@Table(name = "user")
public class UserEntity {

    //    @AssignID("")
//    @AssignID("snowflake")
//    @AutoID
    private String id;

    private String name;

    private Integer age;

    private Date createDate;

    private int delFlag;

}

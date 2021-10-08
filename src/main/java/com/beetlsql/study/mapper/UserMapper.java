package com.beetlsql.study.mapper;

import com.beetlsql.study.pojo.UserEntity;
import org.beetl.sql.core.page.PageRequest;
import org.beetl.sql.mapper.BaseMapper;
import org.beetl.sql.mapper.annotation.Param;
import org.beetl.sql.mapper.annotation.Select;
import org.beetl.sql.mapper.annotation.SpringData;
import org.beetl.sql.mapper.annotation.Sql;
import org.beetl.sql.mapper.annotation.SqlResource;
import org.beetl.sql.mapper.annotation.Template;
import org.beetl.sql.mapper.annotation.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@SqlResource("sys_user")
@Component
public interface UserMapper extends BaseMapper<UserEntity> {


    //类似于mybatis注解查询使用。
    @Sql("select * from sys_user where id = ?")
    @Select
    UserEntity queryUserById(Integer id);

    @Sql("update sys_user set name=? where id = ?")
    @Update
    int updateName(String name,Integer id);

    @Template("select * from sys_user where id = #{id}")
    UserEntity getUserById(@Param("id") Integer myId);

    @SpringData
    List<UserEntity> queryByNameOrderById(String name);


    /**
     * 调用sql文件sys_user.md #select,方法名即markdown片段名字
     * @param id
     * @return
     */
    List<UserEntity> select(@Param("id") Integer id);//必须加@Param

    List<UserEntity> selectPage(@Param("page") PageRequest pageRequest);

    List<UserEntity> selectPageTag(@Param("pageTag") PageRequest pageRequest);

    List<UserEntity> selectPageIgnoreTag(@Param("pageTag") PageRequest pageRequest);




}

package com.beetlsql.study.query;

import com.beetlsql.study.mapper.UserMapper;
import com.beetlsql.study.pojo.UserEntity;
import com.beetlsql.study.utils.SqlManagerUtil;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.query.LambdaQuery;
import org.beetl.sql.core.query.Query;

public class Test {

    public static void main(String[] args) {

        SQLManager sqlManager = SqlManagerUtil.getSqlManager();//获取一个sqlManager
        // 使用sqlManager创建一个lambdaQuery
        LambdaQuery<UserEntity> lambdaQuery = sqlManager.lambdaQuery(UserEntity.class);
        //使用lambdaQuery 构建删除条件
        lambdaQuery.andEq(UserEntity::getDelFlag, 1).delete(); // DELETE FROM `user` WHERE `del_flag` = ?

        // 使用lambdaQuery查询单条
        Object single = lambdaQuery.andEq(UserEntity::getId, 1).single(); // SELECT * FROM `user` WHERE `del_flag` = ? AND `id` = ? limit 0 , 1
        System.out.println(single);

        //使用mapper 方式构建
        UserMapper mapper = sqlManager.getMapper(UserMapper.class);
        LambdaQuery<UserEntity> mapperLambdaQuery = mapper.createLambdaQuery();
        //使用 mapper构建一个Lambda表达式并创建删除条件
        mapperLambdaQuery.andEq(UserEntity::getDelFlag, 1).delete(); // DELETE FROM `user` WHERE `del_flag` = ?
        // 使用mapper创建的Lambda表达式查询单条
        Object mapperSingle = mapperLambdaQuery.andEq(UserEntity::getId, 1).single(); // SELECT * FROM `user` WHERE `del_flag` = ? AND `id` = ? limit 0 , 1
        System.out.println(mapperSingle);

        // 使用sqlManager创建一个Query
        Query<UserEntity> query = sqlManager.query(UserEntity.class);
        // 使用Query 构建一个删除条件
        query.andEq("del_flag", 1).delete(); // DELETE FROM `user` WHERE `del_flag` = ?

        Object querySingle = query.andEq("id", 1).single(); // SELECT * FROM `user` WHERE `del_flag` = ? AND `id` = ? limit 0 , 1
        System.out.println(querySingle);

    }

}

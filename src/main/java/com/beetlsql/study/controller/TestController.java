package com.beetlsql.study.controller;


import com.beetlsql.study.pojo.UserEntity;
import com.beetlsql.study.utils.SqlManagerUtil;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.query.LambdaQuery;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {


    @GetMapping("test/beetl")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public String test() {

        SQLManager sqlManager = SqlManagerUtil.getSqlManager();//获取一个sqlManager
        LambdaQuery<UserEntity> lambdaQuery = sqlManager.lambdaQuery(UserEntity.class); // 使用sqlManager创建一个lambdaQuery
        //使用lambdaQuery 构建删除条件
        lambdaQuery.andEq(UserEntity::getDelFlag, 1).delete();

        // 使用lambdaQuery查询单条
        Object single = lambdaQuery.andEq(UserEntity::getId, 1).single();
        System.out.println(single);
        return "ok";
    }

}

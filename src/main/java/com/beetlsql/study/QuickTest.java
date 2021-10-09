package com.beetlsql.study;

import com.beetlsql.study.mapper.SysUserMapper;
import com.beetlsql.study.pojo.SysUserEntity;
import com.beetlsql.study.utils.SqlManagerUtil;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.SQLReady;
import org.beetl.sql.core.SqlId;
import org.beetl.sql.core.query.LambdaQuery;
import org.beetl.sql.core.query.Query;
import org.beetl.sql.ext.DBInitHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class QuickTest {

    public static void main(String[] args) {

        SQLManager sqlManager = SqlManagerUtil.getSqlManager();
        //按照主键查寻
        SysUserEntity unique = sqlManager.unique(SysUserEntity.class, 1);
        System.out.println(unique);

        //按照模板查询
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setDepartmentId(1);
        List<SysUserEntity> template = sqlManager.template(sysUserEntity);
        System.out.println(template);

        //执行SQL
        String sql = "select * from sys_user where id=?";
        Integer id = 1;
        SQLReady sqlReady = new SQLReady(sql, id);
        List<SysUserEntity> userEntities = sqlManager.execute(sqlReady, SysUserEntity.class);

        String updateSql = "update sys_user set name=? where id =?";
        String name = "lijzz";
        SQLReady updateSqlReady = new SQLReady(updateSql, name, id);
        sqlManager.executeUpdate(updateSqlReady);


        //执行模板SQL
        {
            String select = "select * from sys_user where department_id=#{departmentId} and name= #{name}";
            SysUserEntity paras = new SysUserEntity();
            paras.setDepartmentId(1);
            paras.setName("lihui");
            List<SysUserEntity> list = sqlManager.execute(select, SysUserEntity.class, paras);
            System.out.println(list);
        }

        {
            //或者使用Map作为参数
            String s = "select * from sys_user where department_id=#{myDeptId} and name=#{myName}";
            HashMap<Object, Object> paras = new HashMap<>();
            paras.put("myDeptId", 1);
            paras.put("myName", "lijz");
            List<SysUserEntity> list = sqlManager.execute(s, SysUserEntity.class, paras);
            System.out.println(list);
        }

        //使用Query
        Query<SysUserEntity> query = sqlManager.query(SysUserEntity.class);
        List<SysUserEntity> select = query.andEq("department_id", 1).andIsNotNull("name").select();
        System.out.println(select);


        //使用LambdaQuery，能很好的支持数据库重构
        LambdaQuery<SysUserEntity> lambdaQuery = sqlManager.lambdaQuery(SysUserEntity.class);
        List<SysUserEntity> sysUserEntityList = lambdaQuery.andEq("department_id", 1).andIsNotNull("name").select();
        System.out.println(sysUserEntityList);

        //BeetlSQL推荐一直使用LambdaQuery，Query是JDK7以前的使用方式

        //使用Mapper
        //BeetlSQL3更为推荐的使用Mapper，而不是SQLManager，SQLManger是更为底层的API，使用Mapper能更容易的维护业务代码

        ////得到一个UserMapper接口的代理
        SysUserMapper mapper = sqlManager.getMapper(SysUserMapper.class);
        SysUserEntity entity = mapper.unique(1); //同SQLManager.unique(UserEntity.class,1)
        entity.setName("newName");
        mapper.updateById(entity); //同SQLManager.updateById(me);

        List<SysUserEntity> list = mapper.queryByNameOrderById("lihui");
        System.out.println(list);

        //sqlManager.select将会查询user.md文件下的select片段，并执行，执行结果映射成UserEntity对象。
        SqlId of = SqlId.of("sys_user", "select");
        HashMap<Object, Object> map = new HashMap<>();
        map.put("id", 10);
        List<SysUserEntity> entities = sqlManager.select(of, SysUserEntity.class, map);
        System.out.println(entities);

        List<SysUserEntity> entityList = mapper.select(10);
        System.out.println(entityList);

        //初始化数据脚本，执行后，内存数据库将有一个sys_user表和模拟数据
        DBInitHelper.executeSqlScript(sqlManager, "db/schema.sql");
        //得到数据库的所有表
        Set<String> all = sqlManager.getMetaDataManager().allTable();
        System.out.println(all);
    }
}

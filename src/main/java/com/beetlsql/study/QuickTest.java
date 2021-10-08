package com.beetlsql.study;

import com.beetlsql.study.mapper.UserMapper;
import com.beetlsql.study.pojo.UserEntity;
import com.zaxxer.hikari.HikariDataSource;
import org.beetl.sql.core.ConnectionSource;
import org.beetl.sql.core.ConnectionSourceHelper;
import org.beetl.sql.core.Interceptor;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.SQLManagerBuilder;
import org.beetl.sql.core.SQLReady;
import org.beetl.sql.core.SqlId;
import org.beetl.sql.core.UnderlinedNameConversion;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.core.query.LambdaQuery;
import org.beetl.sql.core.query.Query;
import org.beetl.sql.ext.DebugInterceptor;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;

public class QuickTest {

    private static DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        //内存数据库
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/beetlSql?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&autoReconnect=true&nullCatalogMeansCurrent=true&useSSL=false&serverTimezone=Asia/Shanghai");
        ds.setUsername("root");
        ds.setPassword("plaxyy0708");
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return ds;
    }

    private static SQLManager getSQLManager() {
        //得到一个数据源
        DataSource dataSource = dataSource();
        //得到一个ConnectionSource， 单数据源
        ConnectionSource source = ConnectionSourceHelper.getSingle(dataSource);
        //SQLManagerBuilder 唯一必须的参数就是ConnectionSource
        SQLManagerBuilder builder = new SQLManagerBuilder(source);
        //命名转化，数据库表和列名下划线风格，转化成Java对应的首字母大写，比如create_time 对应createTime
        builder.setNc(new UnderlinedNameConversion());
        //拦截器，非必须，这里设置一个debug拦截器，可以详细查看执行后的sql和sql参数
        builder.setInters(new Interceptor[]{new DebugInterceptor()});
        //数据库风格，因为用的是H2,所以使用H2Style,
//        builder.setDbStyle(new H2Style());
        builder.setDbStyle(new MySqlStyle());
        return builder.build();
    }


    public static void main(String[] args) {

        SQLManager sqlManager = getSQLManager();
        //按照主键查寻
        UserEntity unique = sqlManager.unique(UserEntity.class, 1);
        System.out.println(unique);

        //按照模板查询
        UserEntity userEntity = new UserEntity();
        userEntity.setDepartmentId(1);
        List<UserEntity> template = sqlManager.template(userEntity);
        System.out.println(template);

        //执行SQL
        String sql = "select * from sys_user where id=?";
        Integer id = 1;
        SQLReady sqlReady = new SQLReady(sql, id);
        List<UserEntity> userEntities = sqlManager.execute(sqlReady, UserEntity.class);

        String updateSql = "update sys_user set name=? where id =?";
        String name = "lijzz";
        SQLReady updateSqlReady = new SQLReady(updateSql, name, id);
        sqlManager.executeUpdate(updateSqlReady);


        //执行模板SQL
        {
            String select = "select * from sys_user where department_id=#{departmentId} and name= #{name}";
            UserEntity paras = new UserEntity();
            paras.setDepartmentId(1);
            paras.setName("lihui");
            List<UserEntity> list = sqlManager.execute(select, UserEntity.class, paras);
            System.out.println(list);
        }

        {
            //或者使用Map作为参数
            String s = "select * from sys_user where department_id=#{myDeptId} and name=#{myName}";
            HashMap<Object, Object> paras = new HashMap<>();
            paras.put("myDeptId", 1);
            paras.put("myName", "lijz");
            List<UserEntity> list = sqlManager.execute(s, UserEntity.class, paras);
            System.out.println(list);
        }

        //使用Query
        Query<UserEntity> query = sqlManager.query(UserEntity.class);
        List<UserEntity> select = query.andEq("department_id", 1).andIsNotNull("name").select();
        System.out.println(select);


        //使用LambdaQuery，能很好的支持数据库重构
        LambdaQuery<UserEntity> lambdaQuery = sqlManager.lambdaQuery(UserEntity.class);
        List<UserEntity> userEntityList = lambdaQuery.andEq("department_id", 1).andIsNotNull("name").select();
        System.out.println(userEntityList);

        //BeetlSQL推荐一直使用LambdaQuery，Query是JDK7以前的使用方式

        //使用Mapper
        //BeetlSQL3更为推荐的使用Mapper，而不是SQLManager，SQLManger是更为底层的API，使用Mapper能更容易的维护业务代码

        ////得到一个UserMapper接口的代理
        UserMapper mapper = sqlManager.getMapper(UserMapper.class);
        UserEntity entity = mapper.unique(1); //同SQLManager.unique(UserEntity.class,1)
        entity.setName("newName");
        mapper.updateById(entity); //同SQLManager.updateById(me);

        List<UserEntity> list = mapper.queryByNameOrderById("lihui");
        System.out.println(list);

        //sqlManager.select将会查询user.md文件下的select片段，并执行，执行结果映射成UserEntity对象。
        SqlId of = SqlId.of("sys_user", "select");
        HashMap<Object, Object> map = new HashMap<>();
        map.put("id", 10);
        List<UserEntity> entities = sqlManager.select(of, UserEntity.class, map);
        System.out.println(entities);

        List<UserEntity> entityList = mapper.select(10);
        System.out.println(entityList);

        //初始化数据脚本，执行后，内存数据库将有一个sys_user表和模拟数据
        //DBInitHelper.executeSqlScript(sqlManager,"db/schema.sql");
        // 得到数据库的所有表
        //Set<String> all =  sqlManager.getMetaDataManager().allTable();
        //System.out.println(all);
    }
}

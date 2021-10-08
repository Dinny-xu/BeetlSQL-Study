package com.beetlsql.study.manager;

import com.beetlsql.study.mapper.UserMapper;
import com.beetlsql.study.pojo.UserEntity;
import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.val;
import org.beetl.sql.core.ConnectionSource;
import org.beetl.sql.core.ConnectionSourceHelper;
import org.beetl.sql.core.Interceptor;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.SQLManagerBuilder;
import org.beetl.sql.core.SQLReady;
import org.beetl.sql.core.UnderlinedNameConversion;
import org.beetl.sql.core.db.H2Style;
import org.beetl.sql.core.page.DefaultPageRequest;
import org.beetl.sql.core.page.PageRequest;
import org.beetl.sql.ext.DebugInterceptor;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/*
 * SQLManager是BeetlSQL较为底层的类，用于操作数据库，一个系统允许多个SQLManager，原则上，一个SQLManager对应一个业务库，比如一个SQLManager对应订单库，一个SQLManager对应客户库。
 * 如果是多租户，或者分库分表，应该在一个SQLManger内部实现
 *对于使用BeetlSQL3，掌握SQLManager即可使用，但更推荐的是使用Mapper接口方式，因为他更易于维护。Mapper底层调用了SQLManager API。
 */
public class SqlManager {

    public static void main(String[] args) {

        val sqlManager = getSqlManager();
        //这里，join函数会输出,并记录各个变量分别是1,2,3,4,5
        String sql = "select * from sys_user where id in ( #{join(ids)} )";
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        final HashMap<Object, Object> paras = new HashMap<>();
        paras.put("ids", list);

        List<UserEntity> users = sqlManager.execute(sql, UserEntity.class, paras);
        users.forEach(System.out::println);

        //使用Beetl模板语句
        String beetlSql = "select * from sys_user where 1=1 \n" +
                "-- @if(isNotEmpty(myDeptId)){\n" +
                "   and department_id=#{myDeptId}\t\n" +
                "-- @}\n" +
                "and name=#{myName}";

        final HashMap<Object, Object> map = new HashMap<>();
        map.put("myDeptId", 2);
        map.put("myName", "lj");
        List<UserEntity> userEntities = sqlManager.execute(beetlSql, UserEntity.class, map);
        userEntities.forEach(System.out::println);

        //JDBC SQL翻页查询
        String s = "select * from sys_user where department_id=?";
        PageRequest<UserEntity> request = DefaultPageRequest.of(1, 5);
        SQLReady sqlReady = new SQLReady(s, 1);
        val pr = sqlManager.execute(sqlReady, UserEntity.class, request);


        //模板SQL 翻页查询
        String sq = "select #{page('*')} from sys_user where department_id=#{department_id}";
        val of = DefaultPageRequest.of(1, 3);
        final HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("department_id", 1);
        val pageResult = sqlManager.executePageQuery(sq, UserEntity.class, hashMap, of);
        System.out.println(pageResult);

        /*
         * 使用了函数page(),用于动态生成俩条sql，一条是输出count(1),求总数的，一条是输出page入参的值，用于查询结果
         * 如果sql语句是group by，则还需要封装成子查询
         * */

        String s1 = "select #{page('*')} from (select count(1) total, department_id from sys_user group by department_id ) a";
        //PageRequest对象有一个方法是isTotalRequired，因此可以避免每次都查询，比如,构造PageRequest传入 false
        PageRequest<UserEntity> of1 = DefaultPageRequest.of(1, 10);
        sqlManager.executePageQuery(s1, UserEntity.class, null, of1);

        final UserMapper mapper = sqlManager.getMapper(UserMapper.class);
        final List<UserEntity> pageList = mapper.selectPage(DefaultPageRequest.of(1, 3));
        pageList.forEach(System.out::println);

        //或者使用pageTag
        final List<UserEntity> pageTagList = mapper.selectPageTag(DefaultPageRequest.of(1, 2));
        pageTagList.forEach(System.out::println);

        //为了提高性能,BeetlSQL提供了pageIgnoreTag 标签函数，在求总数的时候忽略order by
        final List<UserEntity> selectPageIgnoreTagList = mapper.selectPageIgnoreTag(DefaultPageRequest.of(1, 3));
        selectPageIgnoreTagList.forEach(System.out::println);


    }


    public static DataSource dataSource() {
        MysqlDataSource source = new MysqlDataSource();
        source.setUrl("jdbc:mysql://localhost:3306/beetlsql?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&autoReconnect=true&nullCatalogMeansCurrent=true&useSSL=false&serverTimezone=Asia/Shanghai");
        source.setUser("root");
        source.setPassword("plaxyy0708");
        return source;
    }

    public static SQLManager getSqlManager() {
        val dataSource = dataSource();
        ConnectionSource source = ConnectionSourceHelper.getSingle(dataSource);
        //source是唯一必须的参数，其他参数都有默认值
        SQLManagerBuilder builder = new SQLManagerBuilder(source);
        //设置NameConversion，这里数据库命名采用下划线风格，使用UnderlinedNameConversion
        builder.setNc(new UnderlinedNameConversion());
        //设置一个拦截器，输出debug日志，包含了sql语句和执行参数，执行时间
        builder.setInters(new Interceptor[]{new DebugInterceptor()});
        //设置数据库分隔，必须跟数据库一样
        builder.setDbStyle(new H2Style());
        return builder.build();
    }
}

package com.beetlsql.study.utils;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.beetl.sql.core.ConnectionSource;
import org.beetl.sql.core.ConnectionSourceHelper;
import org.beetl.sql.core.Interceptor;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.SQLManagerBuilder;
import org.beetl.sql.core.UnderlinedNameConversion;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.ext.DebugInterceptor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class SqlManagerUtil {

    public static DataSource dataSource() {
        MysqlDataSource source = new MysqlDataSource();
        source.setUrl("jdbc:mysql://localhost:3306/beetlsql?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&autoReconnect=true&nullCatalogMeansCurrent=true&useSSL=false&serverTimezone=Asia/Shanghai");
        source.setUser("root");
        source.setPassword("plaxyy0708");
        return source;
    }

    public static SQLManager getSqlManager() {
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

}

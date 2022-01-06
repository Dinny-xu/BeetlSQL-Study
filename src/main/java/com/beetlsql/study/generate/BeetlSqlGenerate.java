package com.beetlsql.study.generate;

import com.beetlsql.study.utils.SqlManagerUtil;
import org.beetl.core.ReThrowConsoleErrorHandler;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.gen.SourceBuilder;
import org.beetl.sql.gen.SourceConfig;
import org.beetl.sql.gen.simple.ConsoleOnlyProject;
import org.beetl.sql.gen.simple.EntitySourceBuilder;
import org.beetl.sql.gen.simple.MDSourceBuilder;
import org.beetl.sql.gen.simple.MapperSourceBuilder;

import java.util.ArrayList;
import java.util.List;

public class BeetlSqlGenerate {

    public static void main(String[] args) {

        List<SourceBuilder> sourceBuilders = new ArrayList<>();
        EntitySourceBuilder entitySourceBuilder = new EntitySourceBuilder();
        MapperSourceBuilder mapperSourceBuilder = new MapperSourceBuilder();
        MDSourceBuilder mdSourceBuilder = new MDSourceBuilder();

        sourceBuilders.add(entitySourceBuilder);
        sourceBuilders.add(mapperSourceBuilder);
        sourceBuilders.add(mdSourceBuilder);

        SQLManager sqlManager = SqlManagerUtil.getSqlManager();
        SourceConfig config = new SourceConfig(sqlManager, sourceBuilders);
        EntitySourceBuilder.getGroupTemplate().setErrorHandler(new ReThrowConsoleErrorHandler());

        ConsoleOnlyProject project = new ConsoleOnlyProject();

        String tableName = "sys_user";
        config.gen(tableName, project);

    }

}

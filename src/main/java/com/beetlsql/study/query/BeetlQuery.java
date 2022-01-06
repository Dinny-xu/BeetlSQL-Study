package com.beetlsql.study.query;


import cn.hutool.core.util.StrUtil;
import com.beetlsql.study.mapper.UserMapper;
import com.beetlsql.study.pojo.UserEntity;
import com.beetlsql.study.utils.SqlManagerUtil;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.page.PageResult;
import org.beetl.sql.core.query.LambdaQuery;
import org.beetl.sql.core.query.Query;
import org.beetl.sql.core.query.interfacer.StrongValue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * BeetlSql 单表查询工具（Query）使用说明
 * <p>
 * 在实际应用场景中大部分时候是在针对单表进行操作，单独的写一条单表操作的SQL较为繁琐，为了能进行高效、快捷、优雅的进行单表操作，Query查询器诞生了。
 */
public class BeetlQuery {

    public static void main(String[] args) {

        SQLManager sqlManager = SqlManagerUtil.getSqlManager();
        UserMapper mapper = sqlManager.getMapper(UserMapper.class);

        //我们以一个 User表为例，查询模糊查询用户名包含 "s" ，并且create_time 不为空的数据库，按照id 倒序。
        //JDK8 强烈推荐使用！！
        LambdaQuery<UserEntity> lambdaQuery = sqlManager.lambdaQuery(UserEntity.class);
        List<UserEntity> list = lambdaQuery.andLike("name", "%s%").andIsNotNull("age").orderBy("id desc").select();
        list.forEach(System.out::println);

        //通用方式，会逐渐抛弃 --> 查看源码发现lambdaQuery 方法更多，这算是query的升级吧
        Query<UserEntity> query = sqlManager.query(UserEntity.class);
        List<UserEntity> userEntityList = query.andLike("name", "%s%").andIsNotNull("age").orderBy("id desc").select();
        userEntityList.forEach(System.out::println);

        // 使用mapper 调用
        List<UserEntity> userEntities = mapper.createLambdaQuery().andLike("name", "%s%").andIsNotNull(UserEntity::getAge).desc(UserEntity::getId).select();

        /*
         * Query主要操作简介
         * Query接口分为俩类：
         * 一部分是触发查询和更新操作，api分别是
         * select 触发查询，返回指定的对象列表
         * single 触发查询，返回一个对象，如果没有，返回null
         * unique 触发查询，返回一个对象，如果没有，或者有多个，抛出异常
         * count  对查询结果集求总数
         * delete 删除符合条件的结果集
         * update 全部字段更新，包括更新null值
         * updateSelective 更新选中的结果集（null不更新）
         * insert 全部字段插入，包括插入null值
         * insertSelective 有选择的插入，null不插入
         */

        // SELECT简单的条件查询
        // SELECT * FROM `user` WHERE `id` BETWEEN 1 AND 1640 AND `name` LIKE '%t%' AND `create_time` IS NOT NULL ORDER BY id desc

        List<UserEntity> entities = mapper.createLambdaQuery()
                .andBetween("id", 1, 6)
                .andLike(UserEntity::getName, "%s%")
                .andIsNotNull(UserEntity::getCreateDate)
                .desc(UserEntity::getId)
                .select();
        entities.forEach(System.out::println);

        //如果我们只要查询其中的几个字段
        List<UserEntity> selectColumn = mapper.createLambdaQuery().select(UserEntity::getId, UserEntity::getName);
        selectColumn.forEach(System.out::println);

        //SELECT name,id FROM `user` WHERE `id` = 1637 AND `create_time` < now() AND `name` = 'test'
        List<UserEntity> entityList = mapper.createLambdaQuery()
                .andEq(UserEntity::getId, 4)
                .andLess(UserEntity::getCreateDate, new Date())
                .andEq(UserEntity::getName, "dss")
                .select();
        entityList.forEach(System.out::println);


        //复杂的条件查询
        /*
         * SQL：SELECT * FROM `user` WHERE ( `id` IN( ? , ? , ? ) AND `name` LIKE ? )OR ( `id` = ? )
         * 参数：[1637, 1639, 1640, %t%, 1640]
         */

        // 打印的SQL 数据库执行为5条记录 -> 此处控制台打印的SQL 为1条记录
        List<UserEntity> selectComplex = mapper.createLambdaQuery()
                .andIn(UserEntity::getId, Collections.singleton("1,2,3,4,5,6"))
                .andLike(UserEntity::getName, "%s%")
                .orEq(UserEntity::getId, 1)
                .select();
        selectComplex.forEach(System.out::println);

        //打印的SQL 数据库执行为5条记录 -> 此处控制台打印的SQL 为1条记录
        List<UserEntity> select = mapper.createLambdaQuery()
                .or(lambdaQuery.condition())
                .andIn(UserEntity::getId, Collections.singleton("1,2,3,4,5,6"))
                .andLike(UserEntity::getName, "%s%")
                .or(lambdaQuery.condition().andEq(UserEntity::getId, 1))
                .select();
        select.forEach(System.out::println);

        // 使用condition ：SQL：SELECT * FROM `user` WHERE ( `id` IN ( ? , ? , ? , ? , ? , ? ) AND `name` LIKE ? )OR ( `id` = ? )
        List<UserEntity> userEntities1 = query
                .or(query.condition()
                        .andIn("id", Arrays.asList(1, 2, 3, 4, 5, 6))
                        .andLike("name", "%s%"))
                .or(query.condition().andEq("id", 1))
                .select();
        userEntities1.forEach(System.out::println);

        // 多种测试结果表明 -> 使用IN 必须要condition() 函数支持
        List<UserEntity> userEntities2 = query
//                .or(query.condition())
                .andIn("id", Arrays.asList(1, 2, 3, 4, 5, 6))
                .andLike("name", "%s%")
//                .or(query.condition())
                .orEq("id", 1).select();
        userEntities2.forEach(System.out::println);

        //SQL：SELECT * FROM `user` WHERE ( `id` IN( ? , ? , ? ) AND `name` LIKE ? )AND `id` = ? OR ( `name` = ? )
        //参数：[1637, 1639, 1640, %t%, 1640, new name2]

        // condition 的主要作用还是加括号
        List<UserEntity> select1 = query
                .or(query.condition()
                        .andIn("id", Arrays.asList(1, 2, 3, 4, 5, 6))
                        .andLike("name", "%s%"))
                .andEq("id", 2)
                .or(query.condition().orEq("name", "dss"))
                .select();
        select1.forEach(System.out::println);

        /* 查询字段智能处理
         * 健壮的变量
         * 在我们开发中，经常会遇到前端传过来一个搜索条件，后端根据搜索条件进行判断，不为空时加入查询条件。
         *
         * 例如前端一个用户列表，有一个根据用户名进行查询的搜索框，我们一般会这么写。
         * public UserEntity findUser(String name){
         *   LambdaQuery<UserEntity> query = sqlManager.lambdaQuery(UserEntity.class);
         *      query.andEq(UserEntity::getDeleteFlag,0);
         *       if(StringUtil.isNotEmpty(userName)){
         *          query.andEq(UserEntity::getUserName,userName);
         *        }
         *        return query.single();
         *    }
         * 如果有很多个这样的字段查询，这样会显得比较臃肿，beetlsql很好的解决了这个问题。
         * Query工具中内置了两个过滤值的静态方法，filterEmpty、filterNull，这两个方法返回了一个StrongValue对象
         * filterEmpty方法主要作用是: 当一个字段为空时不把他加入查询条件，当字段不为空时才加入查询条件。
         * 为空的判断标准：
         *      当字段为String类型，会判断空字符串以及NULL。
         *      当字段为Collection类型以及其子类时，会调用isEmpty方法判断，以及NULL。
         *      当字段为其他对象时，仅仅会判断NULL。
         */

        //要实现上面的代码，我们只要下面这样写就行了。 判断非空 -> 类似于mybatis中 <if test="userName != null and "userName"!= "" "> </if>
        String userName = "dss";
        List<UserEntity> select2 = lambdaQuery
                .andIsNotNull(UserEntity::getCreateDate)
                .andEq(UserEntity::getName, Query.filterEmpty(userName))
                .select();
        select2.forEach(System.out::println);

        /*
         * filterNull方法的作用也是类似的，但是此方法只会判断对象是否等于NULL
         *
         * 自定义实现
         *
         * 但是业务场景往往是复杂的，BeetSql也提供了非常好的拓展性，我们看下filterNull 的方法实现。
         *    public static StrongValue filterNull(Object value) {
         *         return new StrongValue() {
         *             @Override
         *             public boolean isEffective() {
         *                 return value != null;
         *             }
         *
         *             @Override
         *             public Object getValue() {
         *                 return value;
         *             }
         *         };
         *     }
         *   这个方法返回了一个StrongValue接口，实现了isEffective和getValue方法，如果isEffective方法返回true的时候表示将 value 加入查询条件,否则不加查询条件。
         *   假如还是上面的场景，但是userName变成了模糊查询，我们可以自定义StrongValue
         *	public static StrongValue filterLikeEmpty(String value) {
         *         return new StrongValue() {
         *             @Override
         *             public boolean isEffective() {
         *                 return StringUtil.isNotEmpty(value);
         *             }
         *
         *             @Override
         *             public Object getValue() {
         *                 return "%"+value+"%";
         *             }
         *         };
         *     }
         */

//        List<UserEntity> select3 = lambdaQuery.andEq(UserEntity::getDelFlag, 0)
//                .andLike(UserEntity::getName, filterLikeEmpty(userName)).select();
//        select3.forEach(System.out::println);


        /*
         *   INSERT操作
         *         全量插入insert 方法
         *         SQL：insert into `user`(`name`,`department_id`,`create_time`)VALUES( ?,?,?)
         *         参数：[new name, null, null]
         */
//        UserEntity userEntity1 = new UserEntity();
//        userEntity1.setName("Dad");
//        userEntity1.setAge(null);
//        query.insert(userEntity1);

        //全量插入，会对所有的值进行插入，即使这个值是NULL；返回影响的行数；
//        UserEntity userEntity = new UserEntity();
//        userEntity.setName("");
//        userEntity.setAge(null);
//        userEntity.setCreateDate(new Date());
//        query.insertSelective(userEntity);

        /*
         * UPDATE操作
         *      update和insert类似,有全量更新和选择更新的方法；
         *      全量更新 update 方法
         */
        //SQL：update `user` set `name`=?,`department_id`=?,`create_time`=? WHERE `id` = ? AND `create_time` < ? AND `name` = ?
        //参数：[new name, null, null, 1637, now(), test]

        UserEntity userEntity = new UserEntity();
        userEntity.setName("dinny-aa");
        lambdaQuery
                .andEq(UserEntity::getId, 1)
                .andLess(UserEntity::getCreateDate, new Date())
                .andEq(UserEntity::getName, "dinny-a")
                .updateSelective(userEntity);

        /*
         * DELETE操作
         * delete操作非常简单，拼接好条件，调用delete方法即可；返回影响的行数。
         */

        lambdaQuery.andEq(UserEntity::getDelFlag, 1).delete();
        mapper.createLambdaQuery().andEq(UserEntity::getDelFlag, 1).delete();

        /*
         * single查询和unique
         * 在beetlSql中还提供了两个用来查询单条数据的方法，single和unique；
         *
         * single单条查询
         * single查询，查询出一条，如果没有，返回null；
         */

        // lambdaQuery 执行 lambdaQuery.andEq(UserEntity::getDelFlag, 1).delete(); 后会缓存SQL 。后续的lambdaQuery在执行时可能会拼接上去
        /*
         * 例如 执行 lambdaQuery.andEq(UserEntity::getDelFlag, 1).delete();
         *
         * 以下  Object single = lambdaQuery.andEq(UserEntity::getId, 1).single(); 打印出来的SQL 会拼接 del_flag = ?
         *
         * 查看源码发现sql的缓存没有清除
         *
         * 以下三种查询方式：
         *      lambdaQuery 会存在这个问题
         *      mapper.createLambdaQuery() 不会存在这个问题
         *      query 不会存在这个问题
         */

        // 查看SQL 发现 没有设定del_flag 条件。为什么会拼接，原因上面已讲清除
        Object single = lambdaQuery.andEq(UserEntity::getId, 1).single();// SELECT * FROM `user` WHERE `del_flag` = ? AND `id` = ? limit 0 , 1
        System.out.println(single);

        Object single1 = mapper.createLambdaQuery().andEq(UserEntity::getId, 1).single(); // SELECT * FROM `user` WHERE `id` = ? limit 0 , 1
        System.out.println(single1);

        Object one = query.andEq("id", 1).single(); // SELECT * FROM `user` WHERE `id` = ? limit 0 , 1
        System.out.println(one);

        Object unique = lambdaQuery.andEq(UserEntity::getId, 1).unique(); // SELECT * FROM `user` WHERE `id` = ? limit 0 , 2
        System.out.println(unique);

        // COUNT查询
        //SQL：	 SELECT COUNT(1) FROM `user` WHERE `name` = ? OR `id` = ? limit 0 , 10
        //参数：	 [new name, 1637]
        long dddd = lambdaQuery.andEq(UserEntity::getName, "dddd").orEq(UserEntity::getId, 5).limit(0, 1).count();
        System.out.println(dddd);

        long count = query.andEq("name", "dddd").orEq("id", 5).limit(1, 1).count();
        System.out.println(count);


        //GROUP分组查询和Having子句
        // SELECT * FROM `user` WHERE `id` IN(1637, 1639, 1640 ) GROUP BY name
        List<UserEntity> select3 = mapper.createLambdaQuery().andIn("id", Arrays.asList(1, 2, 3, 4, 5, 6)).groupBy("name").select();
        select3.forEach(System.out::println);

        //在分组查询之后，我们可能还要进行having筛选，只需要在后面调用having方法，传入条件即可。
        //SELECT * FROM `user` WHERE `id` IN( 1637, 1639, 1640 ) GROUP BY name HAVING `create_time` IS NOT NULL
        List<UserEntity> select4 = mapper
                .createLambdaQuery()
                .andIn("id", Arrays.asList(1, 2, 3, 4, 5, 6))
                .groupBy("name")
                .having(query.condition().andIsNotNull("create_date"))
                .select();
        select4.forEach(System.out::println);

        //分页查询
        List<UserEntity> select5 = query.andEq("name", "dddd")
                .orEq("id", "1")
                .limit(1, 2)
                .select();
        select5.forEach(System.out::println);


        // Page分页查询
        Query<UserEntity> query1 = query.andEq("del_flag", 0);
        PageResult<UserEntity> page = query1.page(1, 2);
        System.out.println(page);


    }


    /**
     * 自定义StrongValue
     *
     * @param value
     * @return {@link StrongValue}
     */
    public static StrongValue filterLikeEmpty(String value) {
        return new StrongValue() {
            @Override
            public boolean isEffective() {
                return StrUtil.isNotEmpty(value);
            }

            @Override
            public Object getValue() {
                return "%" + value + "%";
            }
        };
    }

}

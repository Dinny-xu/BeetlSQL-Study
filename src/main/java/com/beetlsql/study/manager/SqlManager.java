package com.beetlsql.study.manager;

import com.beetlsql.study.mapper.SysUserMapper;
import com.beetlsql.study.pojo.SysUserEntity;
import com.beetlsql.study.utils.SqlManagerUtil;
import lombok.val;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.SQLReady;
import org.beetl.sql.core.mapping.StreamData;
import org.beetl.sql.core.page.DefaultPageRequest;
import org.beetl.sql.core.page.PageRequest;

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

        SQLManager sqlManager = SqlManagerUtil.getSqlManager();
        //这里，join函数会输出,并记录各个变量分别是1,2,3,4,5
        String sql = "select * from sys_user where id in ( #{join(ids)} )";
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        final HashMap<Object, Object> paras = new HashMap<>();
        paras.put("ids", list);
        List<SysUserEntity> users = sqlManager.execute(sql, SysUserEntity.class, paras);
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
        List<SysUserEntity> userEntities = sqlManager.execute(beetlSql, SysUserEntity.class, map);
        userEntities.forEach(System.out::println);

        //JDBC SQL翻页查询
        String s = "select * from sys_user where department_id=?";
        PageRequest<SysUserEntity> request = DefaultPageRequest.of(1, 5);
        SQLReady sqlReady = new SQLReady(s, 1);
        val pr = sqlManager.execute(sqlReady, SysUserEntity.class, request);


        //模板SQL 翻页查询
        String sq = "select #{page('*')} from sys_user where department_id=#{department_id}";
        val of = DefaultPageRequest.of(1, 3);
        final HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("department_id", 1);
        val pageResult = sqlManager.executePageQuery(sq, SysUserEntity.class, hashMap, of);
        System.out.println(pageResult);

        /*
         * 使用了函数page(),用于动态生成俩条sql，一条是输出count(1),求总数的，一条是输出page入参的值，用于查询结果
         * 如果sql语句是group by，则还需要封装成子查询
         * */

        String s1 = "select #{page('*')} from (select count(1) total, department_id from sys_user group by department_id ) a";
        //PageRequest对象有一个方法是isTotalRequired，因此可以避免每次都查询，比如,构造PageRequest传入 false
        PageRequest<SysUserEntity> of1 = DefaultPageRequest.of(1, 10);
        sqlManager.executePageQuery(s1, SysUserEntity.class, null, of1);

        final SysUserMapper mapper = sqlManager.getMapper(SysUserMapper.class);
        final List<SysUserEntity> pageList = mapper.selectPage(DefaultPageRequest.of(1, 3));
        pageList.forEach(System.out::println);

        //或者使用pageTag
        final List<SysUserEntity> pageTagList = mapper.selectPageTag(DefaultPageRequest.of(1, 2));
        pageTagList.forEach(System.out::println);

        //为了提高性能,BeetlSQL提供了pageIgnoreTag 标签函数，在求总数的时候忽略order by
        final List<SysUserEntity> selectPageIgnoreTagList = mapper.selectPageIgnoreTag(DefaultPageRequest.of(1, 3));
        selectPageIgnoreTagList.forEach(System.out::println);

        //Stream查询
        // 对于某些查询，结果集包含大量数据，如果一次性返回，可能导致内存益处，可以使用stream查询
        /*
         * public StreamData stream(SqlId sqlId, Class clazz, Object paras) 查询sql文件，返回StreamData对象
         * public StreamData streamExecute(String sqlTemplate, Class clazz,Object para) 查询sql模板。返回Stream对象
         * public StreamData streamExecute(SQLReady p, Class clazz) 直接使用jdbc sql查询
         * 需要注意的是，必须在事物上下文里遍历streamData，这是因为StreamData已经脱离了BeetlSQL，但包含了jdbc链接用于加载数据，因此期望事物来自动关闭数据库链接
         */

        SQLReady sqlReady1 = new SQLReady("select * from sys_user");
        StreamData<SysUserEntity> streamData = sqlManager.streamExecute(sqlReady1, SysUserEntity.class);
//        streamData.foreach(System.out::println);
//        streamData.foreach(userEntity -> {
//        });

/*        UserEntity userEntity = new UserEntity();
        userEntity.setName("aa");
        userEntity.setAge(12);
        userEntity.setCreateDate(new Date());
        sqlManager.insert(userEntity);*/

    }
}

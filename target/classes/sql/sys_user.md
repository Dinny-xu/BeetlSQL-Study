select
===

```sql
select *
from sys_user
where id = #{id}
```

selectPage
===

```sql
select #{page("*")}
from sys_user
order by id
```

selectPageTag
===

```sql
select
-- @pageTag(){
id,
name,
department_id,
create_time
-- @ }
from sys_user
```

selectPageIgnoreTag
=== 

* 为了提高性能,BeetlSQL提供了pageIgnoreTag 标签函数，在求总数的时候忽略order by

```sql
select
-- @pageTag(){
id,
name,
department_id,
create_time
-- @ }
from sys_user
-- @pageIgnoreTag(){
order by id
-- @}
```



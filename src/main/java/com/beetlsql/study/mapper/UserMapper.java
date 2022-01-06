package com.beetlsql.study.mapper;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.beetlsql.study.pojo.UserEntity;
import org.beetl.sql.mapper.BaseMapper;
import org.beetl.sql.mapper.annotation.SqlResource;
import org.springframework.stereotype.Component;

@Component
@SqlResource("user")
public interface UserMapper extends BaseMapper<UserEntity> {

    JSONArray fieldSelect(JSONObject jsonObject);
}

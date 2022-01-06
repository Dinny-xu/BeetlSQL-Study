package com.study.test.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 审批实例节点表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CaseNodeDomain {

    private Integer id;
    private String name;
    private Integer age;

}
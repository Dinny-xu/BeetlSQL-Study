package com.study.test;

import com.study.test.pojo.CaseNodeDomain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class demo01 {

    public static void main(String[] args) {
        List<CaseNodeDomain> list = new ArrayList<>();

        CaseNodeDomain caseNodeDomain = new CaseNodeDomain();
        CaseNodeDomain caseNodeDomain1 = new CaseNodeDomain();
        CaseNodeDomain caseNodeDomain2 = new CaseNodeDomain();
        CaseNodeDomain caseNodeDomain3 = new CaseNodeDomain();

        caseNodeDomain.setId(1);
        caseNodeDomain.setName("x1");
        caseNodeDomain.setAge(11);

        caseNodeDomain1.setId(2);
        caseNodeDomain1.setName("x2");
        caseNodeDomain1.setAge(12);

        caseNodeDomain2.setId(3);
        caseNodeDomain2.setName("x3");
        caseNodeDomain2.setAge(13);

        caseNodeDomain3.setId(4);
        caseNodeDomain3.setName("x4");
        caseNodeDomain3.setAge(14);

        list.add(caseNodeDomain);
        list.add(caseNodeDomain1);
        list.add(caseNodeDomain2);
        list.add(caseNodeDomain3);

        Map<Integer, CaseNodeDomain> collect = list.stream().collect(Collectors.toMap(CaseNodeDomain::getId, Function.identity()));
        System.out.println(collect);


    }

}

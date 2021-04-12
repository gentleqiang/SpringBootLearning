package com.gentleman.server.depttree;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class GetDepartmentTree {

     static List<Department> departments = Arrays.asList(
             new Department(1,"研发团队",0),
             new Department(2, "研发团队1", 1),
             new Department(3, "研发团队2", 1),
             new Department(4, "财务部门", 0),
             new Department(5, "财务A部门", 4),
             new Department(6, "财务B部门", 4),
             new Department(7, "财务A部门团队1", 5),
             new Department(8, "财务A部门团队2", 5),
             new Department(9, "财务B部门团队1", 6),
             new Department(10, "财务B部门团队2", 6)
     );

    public static List<Department> getTree(List<Department> list,int parentId){
         //获取所有子节点
         List<Department> childTree = getChildTree(list,parentId);
         childTree.stream().forEach(department -> department.setChildren(getTree(list,department.getId())));
         return childTree;
     }

    public static List<Department> getChildTree(List<Department> list,int id){
         List<Department> childTree = Lists.newArrayList();
         list.stream().forEach(department -> {
             if(department.getParentId() == id){
                 childTree.add(department);
             }
         });
         return childTree;
    }

    public static void main(String[] args) {
        List<Department> trees = GetDepartmentTree.getTree(
                GetDepartmentTree.departments,0);
        trees.stream().forEach(System.out::println);
    }
}

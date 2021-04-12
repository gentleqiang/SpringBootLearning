package com.gentleman.server.depttree;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 部门对象表
 */
public class Department {

    private Integer id;
    private String name;
    private Integer parentId;

    private List<Department> children = Lists.newArrayList();

    public Department(){

    }
    public Department(Integer id, String name, Integer parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<Department> getChildren() {
        return children;
    }

    public void setChildren(List<Department> children) {
        this.children = children;
    }
}

package com.dongzhic;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author dongzhic
 * @Date 2021/11/27 18:59
 */
public class Demo02 {

    public static void main(String[] args) {

        Department d1 = new Department(1L, "学校");
        Department d2 = new Department(2L, "一年级");
        Department d3 = new Department(3L, "二年级");
        Department d4 = new Department(4L, "四年级");
        Department d5 = new Department(5L, "语文");
        Department d6 = new Department(6L, "数学");
        Department d7 = new Department(7L, "语文");
        Department d8 = new Department(8L, "英语");
        Department d9 = new Department(9L, "数学一组");




    }

    /**
     * 通过名称获取部门id
     * @param name 部门名称
     * @return 部门id数组
     */
    public Long[] getId (TreeNode<Department> departments, String name) {

        if (departments == null || name == null || "".equals(name)) {
            return null;
        }

        List<Long> list = new ArrayList<>();

        dfsDepartments(departments, list, name);

        Long[] result = new Long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }

        return result;
    }

    /**
     * 遍历部门树
     * @param departments
     * @param list
     * @param name
     */
    public void dfsDepartments (TreeNode<Department> departments, List<Long> list, String name) {
        if (departments == null) {
            return;
        }

        if (name.equals(departments.department.getName())) {
            list.add(departments.department.getId());
        }

        dfsDepartments(departments.left, list, name);
        dfsDepartments(departments.right, list, name);
    }



}

class TreeNode<Department> {

    public Department department;
    public TreeNode<Department> left;
    public TreeNode<Department> right;

    public TreeNode() {}

    public TreeNode(Department department) {
        this.department = department;
    }

    public TreeNode(Department department, TreeNode<Department> left, TreeNode<Department> right) {
        this.department = department;
        this.left = left;
        this.right = right;
    }
}

class Department {

    private Long id;
    private String name;
    private List<Department> children;

    public Department () {}
    public Department(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public Department(Long id, String name, List<Department> children) {
        this.id = id;
        this.name = name;
        this.children = children;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Department> getChildren() {
        return children;
    }

    public void setChildren(List<Department> children) {
        this.children = children;
    }
}

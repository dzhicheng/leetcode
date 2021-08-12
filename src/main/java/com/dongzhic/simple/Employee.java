package com.dongzhic.simple;

import java.util.List;

/**
 * @Author dongzhic
 * @Date 2021/7/25 17:24
 */
public class Employee {

    /**
     * 唯一的 id
     */
    public int id;
    /**
     * 重要度
     */
    public int importance;
    /**
     * 直系下属的 id
     */
    public List<Integer> subordinates;

}

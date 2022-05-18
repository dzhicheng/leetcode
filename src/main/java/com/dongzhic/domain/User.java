package com.dongzhic.domain;

import lombok.Data;

/**
 * @Author dongzhic
 * @Date 2021/11/26 11:41
 */
@Data
public class User {

    public User () {}
    public User(String username, Integer age) {
        this.username = username;
        this.age = age;
    }

    private String username;
    private Integer age;
}

package com.dongzhic.domain;

import lombok.Data;

/**
 * @Author dongzhic
 * @Date 2021/11/1 11:10
 */
@Data
public class Book {

    /**
     * 名称
     */
    private String name;
    /**
     * 价格
     */
    private Double price;

    public Book() {}
    public Book(String name, Double price) {
        this.name = name;
        this.price = price;
    }
}

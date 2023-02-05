package com.myspringframework.bean;

import java.util.Date;

/**
 * @Author: Itachi
 * @Date: 2023/1/22 17:47
 * @Version: jdk1.8
 * @Description:
 */
public class User {
    private String name;
    private int age;
    private Date date;
    private Address address;

    public User(String name, int age, Date date, Address address) {
        this.name = name;
        this.age = age;
        this.date = date;
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", date=" + date +
                ", address=" + address +
                '}';
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

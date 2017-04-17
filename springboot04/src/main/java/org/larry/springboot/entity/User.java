package org.larry.springboot.entity;

import javax.validation.constraints.Min;

/**
 * Created by Larry on 2017/3/31.
 */
public class User {

    private int id ;
    private String name ;

    @Min(value = 18,message = "未成年少女不得进入！")
    private int age ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

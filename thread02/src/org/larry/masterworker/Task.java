package org.larry.masterworker;

/**
 * Created by Larry on 2017/3/30.
 * 任务类
 */
public class Task {

    private String name ;
    private int id ;
    private int price ;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

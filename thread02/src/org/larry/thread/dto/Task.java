package org.larry.thread.dto;

/**
 * Created by Larry on 2017/3/26.
 */
public class Task implements Comparable<Task> {

    private int id ;
    private String name ;

    public int getId() {
        return id;
    }

    public Task(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(Task o) {
        return this.id < o.id ? 1:(this.id > o.id ? -1 : 0);
    }
}

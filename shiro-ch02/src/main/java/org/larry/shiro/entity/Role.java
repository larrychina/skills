package org.larry.shiro.entity;

/**
 * Created by Larry on 2017/3/8.
 */
public class Role {
    private Long id ;
    private String role ;
    private Boolean available ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}

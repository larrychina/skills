package org.larry.shiro.entity;

/**
 * Created by Larry on 2017/3/8.
 */
public class Permission {
    private Long id ;
    private String permission ;
    private Boolean available ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}

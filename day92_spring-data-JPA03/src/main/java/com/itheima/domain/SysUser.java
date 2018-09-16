package com.itheima.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: yangfei
 * @Date: 2018/9/16 15:25
 * @Description:
 */
@Entity
@Table(name="sys_user")
public class SysUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId ;

    @Column
    private String username;

    @Column
    private String password;

    // 用户角色多对多 : 依靠中间表去维护关系
    // 确定用户与角色的关系 : 一个用户包含多个角色
    @ManyToMany(targetEntity = SysRole.class,cascade = CascadeType.ALL)
    // 配置中间表
    @JoinTable( name="sys_user_role",   //中间表名
                // 配置当前类在中间表中的列
                joinColumns = {@JoinColumn(name="u_id",referencedColumnName = "user_id")},
                // 配置对方在中间表中的列
                inverseJoinColumns = {@JoinColumn(name="r_id",referencedColumnName = "role_id")}
    )
    private Set<SysRole> roles = new HashSet<>(0);  //初始化

    public Set<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<SysRole> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

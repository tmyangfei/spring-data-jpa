package com.itheima.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: yangfei
 * @Date: 2018/9/16 15:25
 * @Description:    多对多
 */
@Entity // 标记该类为实体类
@Table(name="sys_role") // 标记该类与数据库表的映射
public class SysRole {
    @Id // 主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键生成策略
    @Column(name="role_id") // 主键字段映射
    private Long roleId;

    @Column(name="role_name")   // 表字段映射
    private String roleName;

    // 角色与用户: 多对多 , 依靠一张中间表去维护关系
    /*@ManyToMany(targetEntity = SysUser.class)
    // 配置中间表
    @JoinTable( name="sys_user_role",
                //当前类在中间表中的列与值来源
                joinColumns = {@JoinColumn(name="r_id",referencedColumnName = "role_id")},
                //配置对方在中间表中的列与来源
                inverseJoinColumns = {@JoinColumn(name="u_id",referencedColumnName = "user_id")}
    )*/

    // 角色方放弃维护中间表的权利
    // mappedBy : 由对方成员属性上有@JoinTable注解的属性维护
    @ManyToMany(mappedBy = "roles",cascade = CascadeType.ALL)
    private Set<SysUser> users = new HashSet<>(0);

    public Set<SysUser> getUsers() {
        return users;
    }

    public void setUsers(Set<SysUser> users) {
        this.users = users;
    }

    @Override

    public String toString() {
        return "SysRole{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                '}';
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}

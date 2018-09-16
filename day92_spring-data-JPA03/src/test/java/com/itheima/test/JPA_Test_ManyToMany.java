package com.itheima.test;

import com.itheima.dao.SysRoleDao;
import com.itheima.dao.SysUserDao;
import com.itheima.domain.SysRole;
import com.itheima.domain.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: yangfei
 * @Date: 2018/9/15 18:47
 * @Description:    多对多
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class JPA_Test_ManyToMany {

    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private SysUserDao sysUserDao ;


    @Test
    public void test(){
        System.out.println("多对多验证.....");
    }

    // 多对多保存
    @Test
    @Transactional
    @Rollback(false)
    public void test1(){
        SysUser sysUser = new SysUser();
        sysUser.setUsername("Jack");
        sysUser.setPassword("123");

        SysRole sysRole = new SysRole();
        sysRole.setRoleName("狗群主");

        // 保存
        sysUserDao.save(sysUser);
        sysRoleDao.save(sysRole);
        // 问题 : 中间表没数据
    }

    // 解决中间表没数据的问题 : 配置二者间的关系
    /*
        注意 : 不能配置双向关系 , 会报错,联合主键冲突 duplicate
        原因 : 双方都去维护中间表 , 插入重复的数据
        解决 ; 让任意一方放弃维护中间表的权利(放弃原则 : 被动的一方放弃维护 ,
                                    例如 : 用户与角色 , 用户有选择角色的权利 , 角色方式被动的 , 所以角色方放弃维护中间表的权利 )
     */
    @Test
    @Transactional
    @Rollback(false)
    public void test2(){
        SysUser sysUser = new SysUser();
        sysUser.setUsername("Jack");
        sysUser.setPassword("123");

        SysRole sysRole = new SysRole();
        sysRole.setRoleName("狗群主");

        //解决中间表没数据的问题 : 用户知道与角色之间的关系
        sysUser.getRoles().add(sysRole);

        //解决中间表没数据的问题 : 角色知道对应用户之间的关系
        sysRole.getUsers().add(sysUser);
        // 保存
        sysUserDao.save(sysUser);
        sysRoleDao.save(sysRole);

    }

    // 级联删除 : 通常
    @Test
    @Transactional
    @Rollback(false)
    public void test3(){
        /*
            删除用户的同时,让框架帮我们删除角色
            所以在用户方配置级联
            @ManyToMany(targetEntity=SysRole.class,cascade=CascadeType.All)
         */
       // sysUserDao.delete(4L);
        /*
            删除角色 :
                在角色方配置级联 , 让框架帮我们自动删除对应的用户
                 @ManyToMany(targetEntity=SysUser.class,cascade=CascadeType.All)
         */
        sysRoleDao.delete(4L);
    }
}

package com.jpa.test;

import com.jpa.domain.Customer;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * @Author: yangfei
 * @Date: 2018/9/13 15:53
 * @Description:
 */

public class Demo_1 {

    @Test
    public void func_1(){
        Customer customer = new Customer();
        customer.setCustAddress("白马");
        customer.setCustName("纯情打灰机");
        customer.setCustLevel("VIP");
        customer.setCustIndustry("程序员");

        /**
         * 创建实体管理类工厂，借助Persistence的静态方法获取
         * 		其中传递的参数为持久化单元名称，需要jpa配置文件中指定
         */
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("myJPA");
        // 获取实体管理类
        EntityManager entityManager = factory.createEntityManager();
        // 获取事务对象
        EntityTransaction transaction = entityManager.getTransaction();
        // 开启事务
        transaction.begin();

        // 保存一个实体类对象
        entityManager.persist(customer);

        // 提交事务
        transaction.commit();
        // 释放资源
        entityManager.close();
        factory.close();
    }

}

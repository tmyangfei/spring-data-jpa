package com.jpa.test;

import com.jpa.domain.Customer;
import com.jpa.utils.JPAUtil;
import org.hibernate.jpa.internal.metamodel.EntityTypeImpl;
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

public class JPA_crud {

    @Test
    public void func_save() {
        Customer customer = new Customer();
        customer.setCustAddress("黑马");
        customer.setCustName("纯情小JJ");
        customer.setCustLevel("ORDINARY");
        customer.setCustIndustry("项目经理");

        // 从工具类中获取实体管理类对象
        EntityManager entityManager = JPAUtil.getEntityManager();
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
    }

    // JPA代码模板
    @Test
    public void func_find() {
        // 声明事务对象,实体管理对象
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            // 根据工具类 , 获取实体管理器对象
            entityManager = JPAUtil.getEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();    // 开启事务

            // CRUD操作 --- 更新: 先查询 , 再设置值后更新
            Customer customer = entityManager.find(Customer.class, 2L);
            customer.setCustPhone("15217621299");
            entityManager.merge(customer);
            transaction.commit();   // 提交事务
        } catch (Exception e) {
            transaction.rollback(); // 回滚事务
            e.printStackTrace();
        } finally {
            entityManager.close();  // 释放资源
        }


    }

    @Test
    public void func_update() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("myJPA");
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // 删除一条记录 : 先查询,再删除
        entityManager.remove(entityManager.find(Customer.class, 4L));

        transaction.commit();
        entityManager.close();
    }

    @Test
    public void func_reference() {
        // 声明事务对象,实体管理对象
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            // 根据工具类 , 获取实体管理器对象
            entityManager = JPAUtil.getEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();    // 开启事务

            // CRUD操作--查询 , 延迟加载reference
            Customer reference = entityManager.getReference(Customer.class, 3L);
            // Customer customer1 = entityManager.find(Customer.class, 3L);
            // Customer customer2 = entityManager.find(Customer.class, 3L);
            // System.out.println(customer1.equals(customer2));   // true , 第二次查询的是缓存
            System.out.println("-find-- > 即时加载: 不管有没有用到对象,都会发送sql");
            System.out.println("-getReference-- > 延迟加载: 没有用到对象,控制台没有发送sql");
            transaction.commit();   // 提交事务
        } catch (Exception e) {
            transaction.rollback(); // 回滚事务
            e.printStackTrace();
        } finally {
            entityManager.close();  // 释放资源
        }
    }
}

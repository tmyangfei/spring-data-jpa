package com.jpa.test;

import com.jpa.domain.Customer;
import com.jpa.utils.JPAUtil;
import org.junit.Test;

import javax.persistence.*;
import javax.swing.tree.TreeNode;
import java.util.List;

/**
 * @Author: yangfei
 * @Date: 2018/9/13 15:53
 * @Description: JPA 复杂查询
 * <p>
 * jpql 查询语句 : jpa的查询语言, 把sql中的表名换成类名 , 把sql中的字段名换成类的属性名
 * 例如
 * sql = select * from tb_xxx  where tb_name like ?
 * jpql = from Tbxxx where tbName like ?
 * <p>
 * 需要EntityManager创建一个查询对象：
 * 1、获取Query对象：EntityManager.createQuery(jpql);
 * 2、通过Query对象对占位符赋值
 * 3、通过Query对象设置分页、对象的获取
 */

public class JPA_condition {

    // 查询全部
    @Test
    public void func_findAll() {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = JPAUtil.getEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            String jpql = "FROM Customer";
            Query query = entityManager.createQuery(jpql);
            List<Customer> list = query.getResultList();
            for (Customer customer : list) {
                System.out.println(customer);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    //  条件查询：
    @Test
    public void func_condition() {
        // 声明事务对象,实体管理对象
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            // 根据工具类 , 获取实体管理器对象
            entityManager = JPAUtil.getEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();    // 开启事务

            String jpql = "FROM Customer where custName like ?";
            // 创建Query查询对象
            Query query = entityManager.createQuery(jpql);
            // 给占位符赋值
            query.setParameter(1, "纯情%");

            List<Customer> list = query.getResultList();
            for (Customer customer : list) {
                System.out.println(customer);
            }
            transaction.commit();   // 提交事务
        } catch (Exception e) {
            transaction.rollback(); // 回滚事务
            e.printStackTrace();
        } finally {
            entityManager.close();  // 释放资源
        }
    }

    //  分页查询
    @Test
    public void func_limit() {
        // 声明事务对象,实体管理对象
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            // 根据工具类 , 获取实体管理器对象
            entityManager = JPAUtil.getEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();    // 开启事务

            String jpql = "from Customer";
            Query query = entityManager.createQuery(jpql);
            // 第一页
            //query.setFirstResult(0);   // setFirstResult : 设置分页起始位置 , 从第一条结果开始(对应0索引)
            //query.setMaxResults(2); // setMaxResult ; 设置最大的结果集 , 即页大小 , 每页显示多少条记录

            // 第二页
            query.setFirstResult((2 - 1) * 2);  // (当前页-1)* 页大小
            query.setMaxResults(2);
            List list = query.getResultList();
            for (Object o : list) {
                System.out.println((Customer) o);
            }
            transaction.commit();   // 提交事务
        } catch (Exception e) {
            transaction.rollback(); // 回滚事务
            e.printStackTrace();
        } finally {
            entityManager.close();  // 释放资源
        }
    }

    // 排序查询
    @Test
    public void func_sort() {
        // 声明事务对象,实体管理对象
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            // 根据工具类 , 获取实体管理器对象
            entityManager = JPAUtil.getEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();    // 开启事务

            // jpql查询语句
            String jpql = "FROM Customer ORDER BY custId DESC";
            Query query = entityManager.createQuery(jpql);
            List resultList = query.getResultList();
            for (Object o : resultList) {
                System.out.println(o);
            }
            transaction.commit();   // 提交事务
        } catch (Exception e) {
            transaction.rollback(); // 回滚事务
            e.printStackTrace();
        } finally {
            entityManager.close();  // 释放资源
        }
    }

    // 聚合函数查询 ; count , sum , avg , min , max
    @Test
    public void functional() {
        // 声明事务对象,实体管理对象
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            // 根据工具类 , 获取实体管理器对象
            entityManager = JPAUtil.getEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();    // 开启事务

            //String jpql = "SELECT COUNT(*) FROM Customer";    // 统计
            //String jpql = "select avg(custId) from  Customer";  //平均
            // String jpql = "select sum(custId) from Customer";   // 求和
            // String jpql = "select min(custId) from Customer";   // 求最小
            String jpql = "select max(custId) from Customer";   // 求最大
            Query query = entityManager.createQuery(jpql);
            // getSingleResult : 获取单例的结果
            Long result = (Long) query.getSingleResult();

            System.out.println(result);

            Customer customer = entityManager.find(Customer.class, result);
            System.out.println(customer);


            transaction.commit();   // 提交事务
        } catch (Exception e) {
            transaction.rollback(); // 回滚事务
            e.printStackTrace();
        } finally {
            entityManager.close();  // 释放资源
        }
    }
}

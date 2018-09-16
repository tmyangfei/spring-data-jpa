package com.itheima.test;

import com.itheima.dao.CustomerDao;
import com.itheima.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Cipher;
import java.util.List;

/**
 * @Author: yangfei
 * @Date: 2018/9/14 16:30
 * @Description: 使用JPQL方式进行查询
 * @Query("JPQL查询语句")
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class JPA_Test2_JPQL {
    @Autowired
    private CustomerDao customerDao;

    @Test
    public void test1() {
        List<Customer> customers = customerDao.findByJPQL1();
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    @Test
    public void test2() {
        List<Customer> customers = customerDao.findByJPQL2("纯情%");
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    @Test
    public void test3() {
        Customer customer = customerDao.findByJPQL3("纯情%", 2L);
        System.out.println(customer);
    }

    @Test
    @Transactional    // 执行更新,删除等操作必须要在事务支持的环境下 , 否则抛异常InvalidDataAccessApiUsageException
    @Rollback(false)  // 默认是true,表示会回滚  , 加此注解用于提交
    public void test4() {
        customerDao.findByJPQL4(1L,"澳门");
    }

    @Test
    @Transactional
    @Rollback(false)
    public void test5() {
        customerDao.deleteByJPQL1(3L);
    }

    @Test
    public void test6(){
        List<Customer> customers = customerDao.findBySQL();
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }
}

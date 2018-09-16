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

import java.util.List;

/**
 * @Author: yangfei
 * @Date: 2018/9/14 16:30
 * @Description: 使用findBy方式进行查询
 * @Query("JPQL查询语句")
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class JPA_Test3_Method {
    @Autowired
    private CustomerDao customerDao;

    @Test
    public void test1(){
        List<Customer> customers = customerDao.findByCustNameLike("纯情%");
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    @Test
    public void test2(){
        List<Customer> customers = customerDao.findByCustLevel("VIP");
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    @Test
    public void test3(){
        Customer vip = customerDao.findByCustNameLikeAndCustIdAndCustLevel("%小%", 2L, "VIP");
        System.out.println(vip);
    }
}

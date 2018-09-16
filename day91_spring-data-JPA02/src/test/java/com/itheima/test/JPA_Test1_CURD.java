package com.itheima.test;

import com.itheima.dao.CustomerDao;
import com.itheima.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: yangfei
 * @Date: 2018/9/14 16:30
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class JPA_Test1_CURD {
    @Autowired
    private CustomerDao customerDao;

    // 增加
    @Test
    public void test1(){
        Customer customer = new Customer();
        customer.setCustName("纯情小叶子");
        customer.setCustAddress("水晶之痕");
        customer.setCustLevel("永恒黄金");
        customerDao.save(customer);
    }
    // 查询一个: 立即加载
    @Test
    public void test2(){
        Customer customer = customerDao.findOne(2L);
        System.out.println(customer);
    }

    // 查询一个: 延迟加载
    @Test
    @Transactional
    public void test(){
        Customer customer = customerDao.getOne(2L);
        System.out.println(customer);
    }

    // 查询全部
    @Test
    public void test3(){
        List<Customer> customers = customerDao.findAll();
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    // 主键删除
    @Test
    public void test4(){
        customerDao.delete(4L);
    }
    // 删除全部
    @Test
    public void test5(){
        List<Customer> customers = customerDao.findAll();
        for (Customer customer : customers) {
            customerDao.delete(customer);
        }
    }

    // 修改 , save , 有id更新 , 无id保存
    @Test
    public void test6(){
        Customer customer = customerDao.findOne(3L);

        customer.setCustPhone("112324354");
        customerDao.save(customer);
    }

    // 统计查询 :
    @Test
    public void test7(){
        long count = customerDao.count();
        System.out.println(count);
    }

    // 判断实体是否存在 : exists
    /*
        方式1 : 根据id查询不为空 , 就代表存在 , 反之不存在
                select * from cst_customer where cust_id = ?
        方式2 : 统计条数 , 如果大于0 , 就代表这条记录存在
                select count(*) from cst_customer where cust_id = ?
        springDataJpa 用第二种

     */
    @Test
    public void test8(){
        boolean exists = customerDao.exists(2L);
        System.out.println(exists);
    }

    @Test
    public void strtest(){
        String str1 = "abc";
        String str2 = new String ("abc");
        System.out.println(str1.equals(str2));
    }
}

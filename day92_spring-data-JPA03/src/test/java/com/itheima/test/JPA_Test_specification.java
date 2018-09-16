package com.itheima.test;

import com.itheima.dao.CustomerDao;
import com.itheima.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * @Author: yangfei
 * @Date: 2018/9/15 18:47
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class JPA_Test_specification {

    @Autowired
    private CustomerDao customerDao;

    // specification----复杂查询之---findOne
    @Test
    public void test1(){
        // specification 相当于 拼接 where 语句
        //用匿名内部类来构建查询条件，也就是where后面的部分
        //条件: where
        Specification<Customer> spec = new Specification<Customer>() {
            /**
             * @param root      ：我们操作的实体类的属性
             * @param query     ：自定义查询，一般不用
             * @param cb        ：查询的构造器
             * @return  Predicate
             */
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //1、条件我们要查询谁，就获取谁的属性,通过root的get方法获取对象的属性
                Path<Object> custName = root.get("custName");
                //2、构建查询：cb
                //equal方法：第一个参数是path对象，也就是我们根据谁来查询；第二个参数就是比对的值
                Predicate like = cb.like(custName.as(String.class), "纯情%");
                //3、返回构建的Predicate对象即可
                return like;
            }
        };
        List<Customer> customers = customerDao.findAll(spec);
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    @Test
    public void test2(){
        Specification<Customer> spec = new Specification<Customer>() {

            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<Object> custName = root.get("custName");
                Path<Object> custAddress = root.get("custAddress");
                //2、构建查询：cb
                /**
                 * cb中的方法：
                 *      1、equal方法 ： 第一个参数我们直接传入Path对象即可
                 *      2、like、ge、le、lt、gt这些方法，我们需要调用的是Path对象中的as方法返回的对象的类型
                 */
                Predicate like = cb.like(custName.as(String.class), "%小%");
                Predicate equal = cb.equal(custAddress, "白马");
                Predicate predicate = cb.and(like, equal);
                return predicate;
            }
        };

        List<Customer> customers = customerDao.findAll(spec);
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }
    /**
     * 多条件查询： 以纯情开头，级别是“vip” 的用户
     */
    @Test
    public void test3(){
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<Object> custName = root.get("custName");
                Path<Object> custLevel = root.get("custLevel");
                Predicate like = cb.like(custName.as(String.class),"纯情%");
                Predicate vip = cb.equal(custLevel, "VIP");
                //把多个条件拼接起来
                //Predicate and = cb.and(like, equal);//与操作
                //Predicate or = cb.or(like,equal);//或操作
                //返回的是拼接好的条件
                Predicate predicate = cb.and(like, vip);
                return predicate;
            }
        };
        List<Customer> customers = customerDao.findAll(spec);
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    /**
     * 排序： 根据客户id降序排序
     *   步骤 :
     *      创建sort排序对象 , 指定排序方式 , 排序字段
     *      查询时将sort对象加入参数
     */
    @Test
    public void test4(){
        //排序：
        //Sort构造方法：第一个参数：排序规则；    第二个参数：排序的属性
        Sort sort = new Sort(Sort.Direction.DESC,"custId");  //降序
        List<Customer> customers = customerDao.findAll(sort);
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }
    /**
     * 分页++++排序 查询
     */
    @Test
    public void test5(){
        //分页条件
        /**
         * 分页条件：构造方法
         *     第一个参数：查询的页码 ，从0开始，0代表第一页，1代表第二页
         *     第二个参数：页面大小 ，pageSize
         */
        //Pageable page = new PageRequest(0,2);  第一页
        //Pageable page = new PageRequest(1,2);  //第二页
        //PageRequest pageable = new PageRequest(1,2);
        //如果要分页还要排序，那么用PageRequest另外一个构造方法
        Sort sort = new Sort(Sort.Direction.DESC,"custId");
        Pageable pageable = new PageRequest(1,2,sort);
        Page<Customer> page = customerDao.findAll(null, pageable);

        System.out.println("总记录 :"+page.getTotalElements());
        System.out.println("总页数 :"+page.getTotalPages());
        List<Customer> customers =  page.getContent();
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }
}

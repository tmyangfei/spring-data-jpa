package com.itheima.test;

import com.itheima.dao.CustomerDao;
import com.itheima.dao.LinkManDao;
import com.itheima.domain.Customer;
import com.itheima.domain.LinkMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: yangfei
 * @Date: 2018/9/15 18:47
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class JPA_Test_OneToMany {

    @Autowired
    private CustomerDao customerDao ;
    @Autowired
    private LinkManDao linkManDao ;

    @Test
    public void test1(){
        System.out.println("一对多验证....");
    }

    @Test   // 一对多进行保存  ; 保存原则:先保存主表,再保存从表
    @Transactional
    @Rollback(false)
    public void test2(){
        Customer customer = new Customer();
        customer.setCustName("小林");
        customer.setCustLevel("斗者");

        LinkMan linkMan = new LinkMan();
        linkMan.setLinkAge(20L);
        linkMan.setLinkName("小王");

        // 单向关系 , 客户知道联系人的关系 , 外键值由客户来维护
        customer.getLinkMEN().add(linkMan);
        // 执行完毕3条sql语句 , 2条insert1条update,
        customerDao.save(customer);
        linkManDao.save(linkMan);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void test3(){
        Customer customer = new Customer();
        customer.setCustName("小林");
        customer.setCustLevel("斗者");

        LinkMan linkMan = new LinkMan();
        linkMan.setLinkAge(20L);
        linkMan.setLinkName("小王");
        // 单向关系 : 配置联系人知道与客户的关系 , 外键值由联系人来维护
        // 通过JoinColumn注解
        linkMan.setCustomer(customer);

        // 保存 : 执行2条insert语句 , 外键字段在联系人表中 , 所以没有update语句
        customerDao.save(customer);
        linkManDao.save(linkMan);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void test4(){
        Customer customer = new Customer();
        customer.setCustName("小林");
        customer.setCustLevel("斗者");

        LinkMan linkMan = new LinkMan();
        linkMan.setLinkAge(20L);
        linkMan.setLinkName("小王");
        // 双向关系 :
        // 配置联系人知道与客户的关系 , 客户也知道与联系人的关系
        // 通过JoinColumn注解
        customer.getLinkMEN().add(linkMan);
        linkMan.setCustomer(customer);

        // 保存 : 执行3条sql语句 , 2条insert , 1条update语句 , 这个update语句是客户维护的
        /*
            只保存2条数据 , 如何解决多出来的sql语句 ?
                让一方放弃外键的维护权利 , 由多方去维护外键
                @OneToMany(mappedBy="customer")
                mappedBy : 值为多方这边有@JoinColumn注解的属性名称
            此时 只执行2条insert语句
         */
        customerDao.save(customer);
        linkManDao.save(linkMan);
    }

    // 级联操作 : 操作哪方就在哪方配置级联
    /*
        级联删除原则 :
            从表数据随便删
            主表数据删除原则 :
                有对应从表数据 :
                    1. 默认情况下 , 将外键字段值设置为null,再删除主表数据(有外键维护权利下)
                    2. 若配置了放弃外键维护的权利,则不能删除,因为它不在维护关联关系了,所以就不会去更新从表的数据了
                    3. 配置级联操作 , 在删除主表数据时,删除对应的从表数据
                没有从表数据 : 随便删
     */

    @Test
    @Transactional
    @Rollback(false)
    public void test(){
        // 级联删除 , cascade = CascadeType.All
        customerDao.delete(2L);
    }

    // 级联保存
    @Test
    @Transactional
    @Rollback(false)
    public void test6(){
        Customer customer = new Customer();
        customer.setCustName("小林2");
        customer.setCustLevel("斗者2");

        LinkMan linkMan = new LinkMan();
        linkMan.setLinkAge(20L);
        linkMan.setLinkName("小王2");

        LinkMan linkMan2 = new LinkMan();
        linkMan2.setLinkAge(22L);
        linkMan2.setLinkName("小王3");

        // 双向关系
        customer.getLinkMEN().add(linkMan);
        customer.getLinkMEN().add(linkMan2);
        linkMan.setCustomer(customer);
        linkMan2.setCustomer(customer);

        customerDao.save(customer);     //级联保存 : 只保存客户 , 让框架完成保存联系人的操作
    }
}

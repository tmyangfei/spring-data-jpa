package com.itheima.test;

import com.itheima.dao.CustomerDao;
import com.itheima.dao.LinkManDao;
import com.itheima.dao.SysRoleDao;
import com.itheima.dao.SysUserDao;
import com.itheima.domain.Customer;
import com.itheima.domain.LinkMan;
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

import java.util.Set;

/**
 * @Author: yangfei
 * @Date: 2018/9/15 18:47
 * @Description: 对象导航查询 : 根据已经加载的对象,导航到他所关联的对象,利用类与类之间的关系,来检索对象
 *     通过一方查多方 :  延迟加载
 *     通过多方查一方 :  立即加载
 *     设计优点 : 一方查询多方 , 在多方数据较大时,通过延迟加载,用到的时候才去发送sql查询 , 避免在不需要用的数据上浪费内存,消耗系统性能
 *               多方找一方 , 立即加载 , 不会造成浪费资源, 可以手动设置改为延迟加载
 *      @ManyToOne(fetch=FetchType.LAZY)
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class JPA_Test_ObjectNavigationQuery {

    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private SysUserDao sysUserDao ;

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private LinkManDao linkManDao;

    @Test
    @Transactional
    @Rollback(false)
    public void test1(){
        /*
            一对多对象导航查询 : 通过客户查找联系人
                默认是 延迟加载 --lazyLoading
         */
        Customer customer = customerDao.findOne(1L);
        // 获取联系人
        Set<LinkMan> linkMEN = customer.getLinkMEN();
        for (LinkMan linkMAN : linkMEN) {
            System.out.println(linkMAN);

        }
    }

    @Test
    @Transactional
    @Rollback(false)
    public void test2(){
        /*
            多对一对象导航查询 : 通过联系人查找客户
                默认是 立即加载 -- eagerLoading
                在查询联系人的时候进行了表连接 , 已经查询出了该联系人对应的客户
         */
        // 获取联系人
        LinkMan linkMan = linkManDao.findOne(1L);
        Customer customer = linkMan.getCustomer();
        System.out.println(customer);
    }

    @Test
    @Transactional  // 多对多查询 ,延迟加载
    @Rollback(false)
    public void test3(){
        SysUser user = sysUserDao.findOne(4L);
        Set<SysRole> roles = user.getRoles();
        for (SysRole role : roles) {
            System.out.println(role);
        }
    }
}

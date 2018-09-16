package com.itheima.dao;

import com.itheima.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: yangfei
 * @Date: 2018/9/14 16:16
 * @Description:
 */

/**
 *  spring-data-jpa 要求dao接口必须实现一下2个接口
 *                  JpaRepository<T, ID extends Serializable>  , JpaSpecificationExecutor<T>
 *           JpaRepository<映射实体类对象,主键id类型>: 用来完成基本CRUD操作
 *           JpaSpecificationExecutor<映射实体类对象> : 用于复杂查询(分页查询等操作)
 */
public interface CustomerDao extends JpaRepository<Customer,Long> , JpaSpecificationExecutor<Customer>{

    @Query("from Customer") // 查所有
    public List<Customer> findByJPQL1();

    @Query("from Customer where custName like ?")  // 条件查询
    public List<Customer> findByJPQL2(String name);

    @Query("from Customer where custName like ?1 and custId = ?2") // 参数赋值问题
    public Customer findByJPQL3(String name ,Long id);

    @Query("update Customer set custSource=?2 where custId=?1")
    @Modifying  // 表示当前方法用于更新操作
    public void findByJPQL4(Long id , String source);

    @Query("delete from Customer where custId=?1")
    @Modifying
    public void deleteByJPQL1(Long id);

    /*
        nativeQuery : true , 表示使用sql语句查询 , false,表示使用JPQL语句查询
        value : nativeQuery=true,sql ;   nativeQuery=false , JPQL
     */
    @Query(value = "select * from cst_customer",nativeQuery = true)
    public List<Customer> findBySQL();


    // findBy名称命名查询 ----模糊查询
    public List<Customer> findByCustNameLike(String name);

    // findBy名称命名查询 ----精确查询
    public List<Customer> findByCustLevel(String name);

    // findBy名称命名查询 ----多条件查询
    public Customer findByCustNameLikeAndCustIdAndCustLevel(String name , Long id , String level);

}


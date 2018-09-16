package com.itheima.dao;

import com.itheima.domain.Customer;
import com.itheima.domain.LinkMan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author: yangfei
 * @Date: 2018/9/15 18:41
 * @Description:
 */

public interface LinkManDao extends JpaRepository<LinkMan,Long> ,JpaSpecificationExecutor<LinkMan>{
}

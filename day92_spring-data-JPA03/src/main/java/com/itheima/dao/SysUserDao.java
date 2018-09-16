package com.itheima.dao;

import com.itheima.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author: yangfei
 * @Date: 2018/9/15 18:41
 * @Description:
 */

public interface SysUserDao extends JpaRepository<SysUser,Long> ,JpaSpecificationExecutor<SysUser>{
}

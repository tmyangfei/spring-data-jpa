package com.itheima.dao;

import com.itheima.domain.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author: yangfei
 * @Date: 2018/9/15 18:41
 * @Description:
 */

public interface SysRoleDao extends JpaRepository<SysRole,Long> ,JpaSpecificationExecutor<SysRole>{
}

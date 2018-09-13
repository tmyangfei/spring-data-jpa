package com.jpa.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @Author: yangfei
 * @Date: 2018/9/13 16:26
 * @Description:    JPA实体管理类
 */

public final class JPAUtil {
    private static final EntityManagerFactory FACTORY ;

    static {
        /**
         * 创建实体管理类工厂，借助Persistence的静态方法获取
         * 		其中传递的参数为持久化单元名称，需要jpa配置文件中指定
         */
        FACTORY = Persistence.createEntityManagerFactory("myJPA");
    }

    // 使用管理器工厂创建一个实体管理器类
    public static EntityManager getEntityManager(){
        return FACTORY.createEntityManager();
    }
}

package com.jpa.domain;

import javax.persistence.*;

/**
 * @Author: yangfei
 * @Date: 2018/9/13 15:42
 * @Description:    所有的注解都是使用JPA的规范提供的注解
 */
@Entity // 标记当前类为实体类
@Table(name="cst_customer") // 建立当前实体类和表的映射关系
public class Customer {
    @Id // 声明当前对象属性为主键
    @Column(name="cust_id") // 指定当前属性和数据库表中字段的映射关系
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 配置主键的生成策略 == mysql
    /**
        主键生成策略 :
            1. IDENTITY == >    主键由数据库自动生成,即主键是自增长类型  == > mysql
            2. SEQUENCE == >    根据底层数据库的序列来生成主键 , 条件是数据库支持序列 , == > oracle
            3. TABLE    == >    采用一张数据库表来维护主键 , 一般不用 (生成一张额外的表,维护主键)
            4. AUTO     == >    官方: 根据数据库选择最优的主键策略 , 但是实际上走的还是TABLE策略 , 所以一般不用
     */
   /*  数据库支持序列(oracle) 生成策略配置
    @GeneratedValue(strategy = GenerationType.SEQUENCE, //  主键生成策略==序列
                    generator = "cus"   // 指定引用的生成器
                    )
    @SequenceGenerator(
                    name="cus", // 序列生成器的名字,提供上面引用
                    sequenceName = "seq_customer_id",   // 数据库序列名字
                    initialValue = 1,   // 从1开始
                    allocationSize = 1  // 每次增长1(默认50)
                    000
    )*/
    //@GeneratedValue(strategy = GenerationType.TABLE)
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long custId;

    @Column(name="cust_name")
    private String custName;

    @Column(name="cust_source")
    private String custSource;

    @Column(name="cust_industry")
    private String custIndustry;

    @Column(name="cust_level")
    private String custLevel;

    @Column(name="cust_address")
    private String custAddress;

    @Column(name="cust_phone")
    private String custPhone;


    @Override
    public String toString() {
        return "Customer{" +
                "custId=" + custId +
                ", custName='" + custName + '\'' +
                ", custSource='" + custSource + '\'' +
                ", custIndustry='" + custIndustry + '\'' +
                ", custLevel='" + custLevel + '\'' +
                ", custAddress='" + custAddress + '\'' +
                ", custPhone='" + custPhone + '\'' +
                '}';
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustSource() {
        return custSource;
    }

    public void setCustSource(String custSource) {
        this.custSource = custSource;
    }

    public String getCustIndustry() {
        return custIndustry;
    }

    public void setCustIndustry(String custIndustry) {
        this.custIndustry = custIndustry;
    }

    public String getCustLevel() {
        return custLevel;
    }

    public void setCustLevel(String custLevel) {
        this.custLevel = custLevel;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }
}

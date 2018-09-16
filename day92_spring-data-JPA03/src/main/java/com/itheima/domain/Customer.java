package com.itheima.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: yangfei
 * @Date: 2018/9/15 18:41
 * @Description:
 */
@Entity
@Table(name="cst_customer")
public class Customer implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cust_id")
    private Long custId;

    @Column(name="cust_name")
    private String custName;

    @Column(name="cust_level")
    private String custLevel;

    @Column(name="cust_source")
    private String custSource;

    @Column(name="cust_address")
    private String custAddress;

    @Column(name="cust_phone")
    private String custPhone;

    @Column(name="cust_industry")
    private String custIndustry;

    // 一对多映射关系配置
    // 表关系映射 :一个客户包含多个联系人

    /*@OneToMany(targetEntity = LinkMan.class)
    // 映射外键 : 加入一列
    @JoinColumn(
            name="link_cust_id",    // 外键字段名
            referencedColumnName = "cust_id"    // 外键值来源
    )*/
   // 放弃外键的维护 , 由多方去维护外键, 因为外键字段在多方的表中


    @OneToMany(
            mappedBy = "customer", // mappedBy : 其取值为多方这边有@JoinColumn注解的属性名
            cascade = CascadeType.ALL       // 级联操作 , 所有级联权利
    )
    private Set<LinkMan> linkMEN = new HashSet<>(0);        // hashSet默认开辟16个空间 ,ArrayList开辟10个空间



    public Set<LinkMan> getLinkMEN() {
        return linkMEN;
    }

    public void setLinkMEN(Set<LinkMan> linkMEN) {
        this.linkMEN = linkMEN;
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

    public String getCustLevel() {
        return custLevel;
    }

    public void setCustLevel(String custLevel) {
        this.custLevel = custLevel;
    }

    public String getCustSource() {
        return custSource;
    }

    public void setCustSource(String custSource) {
        this.custSource = custSource;
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

    public String getCustIndustry() {
        return custIndustry;
    }

    public void setCustIndustry(String custIndustry) {
        this.custIndustry = custIndustry;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "custId=" + custId +
                ", custName='" + custName + '\'' +
                ", custLevel='" + custLevel + '\'' +
                ", custSource='" + custSource + '\'' +
                ", custAddress='" + custAddress + '\'' +
                ", custPhone='" + custPhone + '\'' +
                ", custIndustry='" + custIndustry + '\'' +
                '}';
    }
}

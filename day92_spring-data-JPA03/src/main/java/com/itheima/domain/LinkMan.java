package com.itheima.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: yangfei
 * @Date: 2018/9/15 20:12
 * @Description:        联系人 ---- 客户
 *                        一  ->- 一
 */
@Entity
@Table(name="tb_linkman")
public class LinkMan implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="link_id")
    private Long linkId;
    @Column(name="link_name")
    private String linkName ;
    @Column(name="link_sex")
    private String linkSex ;
    @Column(name="link_age")
    private Long linkAge ;

    //  表映射关系 : 多方对一方 , 一个联系人从属于一个客户
    @ManyToOne(targetEntity = Customer.class,
                fetch = FetchType.LAZY // 多方找一方,默认立即,改为延迟
    )
    @JoinColumn(
            name = "link_cust_id",  // 外键字段名
            referencedColumnName = "cust_id"    // 外键值来源 : 主表主键
    )
    private Customer customer ;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    @Override
    public String toString() {
        return "LinkMan{" +
                "linkId=" + linkId +
                ", linkName='" + linkName + '\'' +
                ", linkSex='" + linkSex + '\'' +
                ", linkAge=" + linkAge +
                '}';
    }

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getLinkSex() {
        return linkSex;
    }

    public void setLinkSex(String linkSex) {
        this.linkSex = linkSex;
    }

    public Long getLinkAge() {
        return linkAge;
    }

    public void setLinkAge(Long linkAge) {
        this.linkAge = linkAge;
    }
}

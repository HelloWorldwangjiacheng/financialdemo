package com.imooc.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 产品
 * @author w1586
 */
@Data
@Entity(name = "product")
public class Product implements Serializable{
    @Id
    private String id;
    private String name;
    /**
     * @see com.imooc.entity.enums.ProductStatus
     */
    private String status ;
    /**
     * 起投金额
     */
    private BigDecimal thresholdAmount ;
    /**
     * 投资步长
     */
    private BigDecimal stepAmount;
    /**
     *     锁定期
     */
    private Integer lockTerm;
    /**
     *     收益率，因为要与其他数相乘，所以使用BigDecimal
     */
    private BigDecimal rewardRate;
    private String memo;
    private Date createAt;
    private Date updateAt;
    private String createUser;
    private String updateUser;

    public Product(){}

    public Product(String id, String name, String status, BigDecimal thresholdAmount, BigDecimal stepAmount, BigDecimal rewardRate) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.thresholdAmount = thresholdAmount;
        this.stepAmount = stepAmount;
        this.rewardRate = rewardRate;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", thresholdAmount=" + thresholdAmount +
                ", stepAmount=" + stepAmount +
                ", lockTerm=" + lockTerm +
                ", rewardRate=" + rewardRate +
                ", memo='" + memo + '\'' +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                ", createUser='" + createUser + '\'' +
                ", updateUser='" + updateUser + '\'' +
                '}';
    }

}

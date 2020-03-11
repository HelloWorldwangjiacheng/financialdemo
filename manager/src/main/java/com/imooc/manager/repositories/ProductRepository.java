package com.imooc.manager.repositories;

import com.imooc.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * 产品管理
 * @Author w1586
 * @Date 2020/3/10 23:10
 * @Cersion 1.0
 */
public interface ProductRepository
        extends CrudRepository<Product,String>,JpaRepository<Product, String>, JpaSpecificationExecutor<Product>
{

}

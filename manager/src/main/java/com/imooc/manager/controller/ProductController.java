package com.imooc.manager.controller;

import com.imooc.entity.Product;
import com.imooc.manager.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @Author w1586
 * @Date 2020/3/11 12:59
 * @Cersion 1.0
 */
@RestController
@RequestMapping("/products")
public class ProductController {
    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    /**
     * 添加产品
     *
     * @param product
     * @return 创建的产品result
     */
    @PostMapping("")
    public Product addProduct(@RequestBody Product product) {
        logger.info("创建产品，参数：{}", product);

        Product result = productService.addProduct(product);

        logger.info("创建产品，结果：{}", result);
        return result;
    }

    /**
     * 查询单个产品
     *
     * @param id 产品编号
     * @return 返还对应产品或者null
     */
    @GetMapping("/{id}")
    public Product findOne(@PathVariable String id) {
        logger.info("查询单个产品, id={}", id);

        Product product = productService.findOne(id);

        logger.info("查询单个产品，结果={}", product);

        return product;
    }

    /**
     * 分页查询
     * @param ids
     * @param minRewardRate
     * @param maxRewardRate
     * @param status
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("")
    public Page<Product> queryPage(String ids,
                                   BigDecimal minRewardRate,
                                   BigDecimal maxRewardRate,
                                   String status,
                                   @RequestParam(defaultValue = "0") int pageNum,
                                   @RequestParam(defaultValue = "0") int pageSize) {
        logger.info("查询产品,ids={},minRewardRate={},maxRewardRate={},status={},pageNum={},pageSize={}",
                ids, minRewardRate, maxRewardRate, status, pageNum, pageSize);

        List<String> idList = null, statusList = null;
        if (!StringUtils.isEmpty(ids)) {
            idList = Arrays.asList(ids.split(","));
        }
        if (!StringUtils.isEmpty(status)) {
            statusList = Arrays.asList(status.split(","));
        }

//        Pageable pageable = new PageRequest(pageNum, pageSize);
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Product> page = productService.queryPage(idList, minRewardRate, maxRewardRate, statusList, pageable);
        logger.info("查询产品,结果={}", page);
        return page;
    }

}

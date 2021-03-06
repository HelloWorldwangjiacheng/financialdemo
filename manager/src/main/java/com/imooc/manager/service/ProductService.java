package com.imooc.manager.service;


import com.imooc.entity.Product;
import com.imooc.entity.enums.ProductStatus;
import com.imooc.manager.error.ErrorEnum;
import com.imooc.manager.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 产品服务类
 *
 * @Author w1586
 * @Date 2020/3/10 23:18
 * @Cersion 1.0
 */
@Service
public class ProductService {

    private static Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    /**
     * 创建产品
     *
     * @param product
     * @return
     */
    @Transactional
    public Product addProduct(Product product) {

        logger.debug("创建产品，参数：{}", product);
        // 数据校验
        checkProduct(product);

        // 设置默认值
        setDefault(product);
        Product result = productRepository.save(product);

        logger.debug("创建产品，结束：{}", result);
        return result;
    }

    /**
     * 设置默认值
     * 创建时间、更新时间
     * 投资步长、锁定期、状态
     *
     * @param product
     */
    private void setDefault(Product product) {
        if (product.getCreateAt() == null) {
            product.setCreateAt(new Date());
        }
        if (product.getUpdateAt() == null) {
            product.setUpdateAt(new Date());
        }
        if (product.getLockTerm() == null) {
            product.setLockTerm(0);
        }
        if (product.getStepAmount() == null) {
            product.setStepAmount(BigDecimal.ZERO);
        }
        if (product.getStatus() == null) {
            product.setStatus(ProductStatus.AUDITING.name());
        }
    }

    /**
     * 产品数据校验：
     * 1. 非空数据
     * 2. 收益率要在0~30以内
     * 3. 投资步长需为整数
     *
     * @param product
     */
    private void checkProduct(Product product) {
        Assert.notNull(product.getId(), ErrorEnum.ID_NOT_NULL.getCode());
        // 其他非空校验

        Assert.isTrue(BigDecimal.ZERO.compareTo(product.getRewardRate()) < 0
                        && BigDecimal.valueOf(30).compareTo(product.getRewardRate()) >= 0,
                "收益率范围错误");

        Assert.isTrue(BigDecimal.valueOf(product.getStepAmount().longValue())
                        .compareTo(product.getStepAmount()) == 0,
                "投资步长需为整数");
    }


    /**
     * 查询单个产品
     * @param id 产品编号
     * @return 返还对应产品或者null
     */
    public Product findOne(String id){
        //判断编号不可为空
        Assert.notNull(id,"需要产品编号参数");

        logger.debug("查询单个产品,id={}", id);

        Product product = productRepository.findById(id).get();

        logger.debug("查询单个产品，结果={}",product);
        return product;
    }

    /**
     * 分页查询产品
     * @param idList
     * @param minRewardRate
     * @param maxRewardRate
     * @param statusList
     * @param pageable
     * @return
     */
    public Page<Product> queryPage(List<String> idList,
                               BigDecimal minRewardRate,
                               BigDecimal maxRewardRate,
                               List<String> statusList,
                               Pageable pageable)
    {
        logger.debug("查询产品,idList={},minRewardRate={},maxRewardRate={},statusList={},pageable={}",
                idList, minRewardRate, maxRewardRate, statusList, pageable);

        Specification<Product> specification = new Specification<Product>(){
            @Override
            public Predicate toPredicate(Root<Product> root,
                                         CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder)
            {
                Expression<String> idCol = root.get("id");
                Expression<BigDecimal> rewardRateCol = root.get("rewardRate");
                Expression<String> statusCol = root.get("status");
                // 生成断言列表，其实就是查询条件的拼接
                List<Predicate> predicates = new ArrayList<>();
                if (idList!=null && idList.size()>0){
                    predicates.add(idCol.in(idList));
                }
                if (minRewardRate!=null && BigDecimal.ZERO.compareTo(minRewardRate)<0){
                    predicates.add(criteriaBuilder.ge(rewardRateCol, minRewardRate));
                }
                if (BigDecimal.ZERO.compareTo(maxRewardRate)<0){
                    predicates.add(criteriaBuilder.le(rewardRateCol,maxRewardRate));
                }
                if (statusList!=null && statusList.size()>0){
                    predicates.add(statusCol.in(statusList));
                }

                criteriaQuery.where(predicates.toArray(new Predicate[0]));
                return null;
            }
        };
        Page<Product> page = productRepository.findAll(specification, pageable);

        logger.debug("查询产品,结果={}", page);
        return page;
    }

}

package org.example.smackwebserver.service;

import org.example.smackwebserver.dao.TravelProduct;
import org.example.smackwebserver.dao.TravelProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class TravelProductServiceImpl implements TravelProductService {

    @Autowired
    private TravelProductRepository travelProductRepository;

    @Override
    public TravelProduct getTravelProductById(long id) {
        // 如果找不到记录，抛出自定义异常或返回 null
        return travelProductRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Travel product with id " + id + " not found"));
    }

    @Override
    public Long createTravelProduct(TravelProduct travelProduct) {

        // 校验规则

        // 1. 产品标题
        if (travelProduct.getTitle() == null || travelProduct.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Product title cannot be empty");
        }
        if (travelProduct.getTitle().length() > 100) {
            throw new IllegalArgumentException("Product title length cannot exceed 100 characters");
        }

        // 2. 出发地与目的地
        if (travelProduct.getDepartureLocation() == null || travelProduct.getDepartureLocation().trim().isEmpty()) {
            throw new IllegalArgumentException("Departure location cannot be empty");
        }
        if (travelProduct.getDestination() == null || travelProduct.getDestination().trim().isEmpty()) {
            throw new IllegalArgumentException("Destination cannot be empty");
        }
        if (travelProduct.getDepartureLocation().equalsIgnoreCase(travelProduct.getDestination())) {
            throw new IllegalArgumentException("Departure location and destination cannot be the same");
        }
        if (travelProduct.getDepartureLocation().length() > 100 || travelProduct.getDestination().length() > 100) {
            throw new IllegalArgumentException("Location fields cannot exceed 100 characters");
        }

        // 3. 发团时间
        if (travelProduct.getStartDate() == null || travelProduct.getEndDate() == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }
        if (travelProduct.getStartDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Start date cannot be in the past");
        }
        if (travelProduct.getEndDate().isBefore(travelProduct.getStartDate())) {
            throw new IllegalArgumentException("End date must be after the start date");
        }

        // 4. 最大容纳人数
        if (travelProduct.getMaxCapacity() <= 0) {
            throw new IllegalArgumentException("Maximum capacity must be greater than zero");
        }

        // 5. 产品价格
        if (travelProduct.getPrice() == null || travelProduct.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Product price must be a positive value");
        }

        // 6. 产品主题和类型（可选字段）
        if (travelProduct.getTheme() != null && travelProduct.getTheme().length() > 50) {
            throw new IllegalArgumentException("Theme length cannot exceed 50 characters");
        }
        if (travelProduct.getProductType() != null && travelProduct.getProductType().length() > 50) {
            throw new IllegalArgumentException("Product type length cannot exceed 50 characters");
        }

        // 保存到数据库
        travelProduct.setCreatedAt(LocalDate.now());
        travelProduct.setUpdatedAt(LocalDate.now());
        TravelProduct savedProduct = travelProductRepository.save(travelProduct);

        // 返回产品 ID
        return (long) savedProduct.getId();
    }
}


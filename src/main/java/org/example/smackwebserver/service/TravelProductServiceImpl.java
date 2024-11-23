package org.example.smackwebserver.service;

import org.example.smackwebserver.dao.TravelProduct;
import org.example.smackwebserver.dao.TravelProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        travelProduct.setCreatedAt(LocalDateTime.now());
        travelProduct.setUpdatedAt(LocalDateTime.now());
        TravelProduct savedProduct = travelProductRepository.save(travelProduct);

        // 返回产品 ID
        return (long) savedProduct.getId();
    }

    @Override
    public Long updateTravelProduct(TravelProduct travelProduct) {
        // 检查是否存在指定 ID 的产品
        TravelProduct existingProduct = travelProductRepository.findById((long)travelProduct.getId())
                .orElseThrow(() -> new IllegalArgumentException("Travel product with ID " + travelProduct.getId() + " does not exist"));

        // 更新非空字段
        if (travelProduct.getTitle() != null) {
            existingProduct.setTitle(travelProduct.getTitle());
        }
        if (travelProduct.getUrl() != null) {
            existingProduct.setUrl(travelProduct.getUrl());
        }
        if (travelProduct.getStartDate() != null) {
            existingProduct.setStartDate(travelProduct.getStartDate());
        }
        if (travelProduct.getEndDate() != null) {
            existingProduct.setEndDate(travelProduct.getEndDate());
        }
        if (travelProduct.getFeatures() != null) {
            existingProduct.setFeatures(travelProduct.getFeatures());
        }
        if (travelProduct.getTheme() != null) {
            existingProduct.setTheme(travelProduct.getTheme());
        }
        if (travelProduct.getDepartureLocation() != null) {
            existingProduct.setDepartureLocation(travelProduct.getDepartureLocation());
        }
        if (travelProduct.getDestination() != null) {
            existingProduct.setDestination(travelProduct.getDestination());
        }
        if (travelProduct.getMaxCapacity() > 0) { // 假设负值无意义
            existingProduct.setMaxCapacity(travelProduct.getMaxCapacity());
        }
        if (travelProduct.getPrice() != null && travelProduct.getPrice().compareTo(BigDecimal.ZERO) > 0) {
            existingProduct.setPrice(travelProduct.getPrice());
        }
        if (travelProduct.getProductType() != null) {
            existingProduct.setProductType(travelProduct.getProductType());
        }

        // 更新更新时间
        existingProduct.setUpdatedAt(LocalDateTime.now());

        // 保存更新后的产品
        travelProductRepository.save(existingProduct);

        return (long) existingProduct.getId();
    }

    @Override
    public void deleteTravelProductById(long id) {
        if (!travelProductRepository.existsById(id)) {
            throw new IllegalArgumentException("Travel product with ID " + id + " does not exist");
        }

        // 删除产品
        travelProductRepository.deleteById(id);
    }

    @Override
    public Map<String, Object> searchTravelProducts(Integer userId, String productType, String theme,
                                                    String departureLocation, String destination, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<TravelProduct> productPage = travelProductRepository.searchTravelProducts(
                userId, productType, theme, departureLocation, destination, pageable);

        // 将数据与总记录数封装为 Map 返回
        Map<String, Object> result = new HashMap<>();
        result.put("total_item", productPage.getTotalElements()); // 总记录数
        result.put("data", productPage.getContent()); // 当前页的数据

        return result;
    }
    @Override
    public Page<TravelProduct> searchProductsByKeyword(String keyword, Pageable pageable) {
        return travelProductRepository.searchByKeyword(keyword, pageable);
    }

    @Override
    public Map<String, Object> searchTravelProductsWithKeyword(Integer userId, String productType, String theme,
                                                               String departureLocation, String destination,
                                                               String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        // 调用组合查询方法
        Page<TravelProduct> productPage = travelProductRepository.searchByConditionsAndKeyword(
                userId, productType, theme, departureLocation, destination, keyword, pageable);

        // 将数据与总记录数封装为 Map 返回
        Map<String, Object> result = new HashMap<>();
        result.put("total_item", productPage.getTotalElements()); // 总记录数
        result.put("data", productPage.getContent()); // 当前页的数据

        return result;
    }

}


package org.example.smackwebserver.dao;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "travel_product")
public class TravelProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id; // 产品 ID

    @Column(name = "user_id", nullable = false)
    private int userId; // 发布者的用户 ID

    @Column(name = "title", nullable = false, length = 100)
    private String title; // 产品标题

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate; // 发团开始时间

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate; // 发团结束时间

    @Column(name = "features", columnDefinition = "TEXT")
    private String features; // 产品特色

    @Column(name = "theme", length = 50)
    private String theme; // 产品主题

    @Column(name = "departure_location", nullable = false, length = 100)
    private String departureLocation; // 出发地

    @Column(name = "destination", nullable = false, length = 100)
    private String destination; // 目的地

    @Column(name = "max_capacity", nullable = false)
    private int maxCapacity; // 最大容纳人数

    @Column(name = "product_type", length = 50)
    private String productType; // 产品类型

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price; // 产品价格

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt; // 创建时间

    @Column(name = "updated_at", nullable = false)
    private LocalDate updatedAt; // 更新时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
}

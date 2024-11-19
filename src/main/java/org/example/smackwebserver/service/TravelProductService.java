package org.example.smackwebserver.service;

import org.example.smackwebserver.dao.TravelProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface TravelProductService {
    TravelProduct getTravelProductById(long id);

    Long createTravelProduct(TravelProduct travelProduct);

    Long updateTravelProduct(TravelProduct travelProduct);

    void deleteTravelProductById(long id);

    Map<String, Object> searchTravelProducts(Integer userId, String productType, String theme, String departureLocation, String destination, int page, int size);

    Page<TravelProduct> searchProductsByKeyword(String keyword, Pageable pageable);
}

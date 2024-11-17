package org.example.smackwebserver.service;

import org.example.smackwebserver.dao.TravelProduct;

import java.util.List;

public interface TravelProductService {
    TravelProduct getTravelProductById(long id);

    Long createTravelProduct(TravelProduct travelProduct);

    Long updateTravelProduct(TravelProduct travelProduct);

    void deleteTravelProductById(long id);

    List<TravelProduct> searchTravelProducts(Integer userId, String productType, String theme, String departureLocation, String destination, int page, int size);
}

package org.example.smackwebserver.service;

import org.example.smackwebserver.dao.TravelProduct;

public interface TravelProductService {
    TravelProduct getTravelProductById(long id);

    Long createTravelProduct(TravelProduct travelProduct);

    Long updateTravelProduct(TravelProduct travelProduct);

    void deleteTravelProductById(long id);
}

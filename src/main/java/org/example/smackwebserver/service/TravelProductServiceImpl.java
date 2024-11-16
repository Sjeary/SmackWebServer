package org.example.smackwebserver.service;

import org.example.smackwebserver.dao.TravelProduct;
import org.example.smackwebserver.dao.TravelProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TravelProductServiceImpl implements TravelProductService {

    @Autowired
    private TravelProductRepository travelProductRepository;

    @Override
    public TravelProduct getTravelProductById(long id) {
        TravelProduct travelProduct = travelProductRepository.findById(id).orElseThrow(RuntimeException::new);
        return travelProduct;
    }
}

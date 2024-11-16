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
        // 如果找不到记录，抛出自定义异常或返回 null
        return travelProductRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Travel product with id " + id + " not found"));
    }
}


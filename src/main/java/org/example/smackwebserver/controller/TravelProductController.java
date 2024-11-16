package org.example.smackwebserver.controller;

import org.example.smackwebserver.Response;
import org.example.smackwebserver.dao.TravelProduct;
import org.example.smackwebserver.service.TravelProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TravelProductController {

    @Autowired
    private TravelProductService travelProductService;

    @GetMapping("/api/v1/TravelProduct/{id}")
    public Response<TravelProduct> getTravelProduct(@PathVariable("id") long id) {
        return Response.newSuccess(travelProductService.getTravelProductById(id));
    }
}

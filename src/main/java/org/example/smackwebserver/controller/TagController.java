package org.example.smackwebserver.controller;

import org.example.smackwebserver.Response;
import org.example.smackwebserver.dao.Dynamic;
import org.example.smackwebserver.service.DynamicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class TagController {
    private final DynamicService dynamicService;

    public TagController(DynamicService dynamicService) {
        this.dynamicService = dynamicService;
    }

    @GetMapping("/Tag/{tagName}")
    public Response<Map<String, Object>> getDynamicsByTag(
            @PathVariable String tagName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Map<String, Object> result = dynamicService.searchDynamicsByTagName(tagName, page, size);
            return Response.newSuccess(result);
        } catch (Exception e) {
            return Response.newFail("Failed to search travel products: " + e.getMessage());
        }
    }
}

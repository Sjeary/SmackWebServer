package org.example.smackwebserver.controller;

import org.example.smackwebserver.Response;
import org.example.smackwebserver.dao.Dynamic;
import org.example.smackwebserver.service.DynamicService;
import org.example.smackwebserver.service.TagService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class TagController {
    private final DynamicService dynamicService;
    private final TagService tagService;

    public TagController(DynamicService dynamicService, TagService tagService) {
        this.dynamicService = dynamicService;
        this.tagService = tagService;
    }

    @GetMapping("/Tag")
    public Response<Map<String, Object>> getAllTags(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Map<String, Object> result = tagService.getAllTags(page, size);
            return Response.newSuccess(result);
        } catch (Exception e) {
            return Response.newFail("Failed to get tags: " + e.getMessage());
        }
    }

    @GetMapping("/Tag/search")
    public Response<Map<String, Object>> searchTags(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Map<String, Object> result = tagService.searchTag(keyword, page, size);
            return Response.newSuccess(result);
        } catch (Exception e) {
            return Response.newFail("Failed to get tags: " + e.getMessage());
        }
    }

    @GetMapping("/Tag/{id}")
    public Response<Map<String, Object>> getDynamicsByTag(
            @PathVariable int id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Map<String, Object> result = dynamicService.searchDynamicsByTagId(id, page, size);
            return Response.newSuccess(result);
        } catch (Exception e) {
            return Response.newFail("Failed to search dynamics with tag " + id + " : " + e.getMessage());
        }
    }
}

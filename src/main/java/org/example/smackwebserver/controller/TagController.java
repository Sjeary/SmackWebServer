package org.example.smackwebserver.controller;

import org.example.smackwebserver.Response;
import org.example.smackwebserver.dao.Dynamic;
import org.example.smackwebserver.dao.DynamicComment;
import org.example.smackwebserver.dao.Tag;
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

    @GetMapping("/Tag/list")
    public Response<List<String>> getTagList() {
        try {
            List<String> tagList = tagService.getTagList();
            return Response.newSuccess(tagList);
        } catch (Exception e) {
            return Response.newFail("Failed to get tagList: " + e.getMessage());
        }
    }

    @PostMapping("/Tag")
    public Response<Tag> createTag(@RequestBody Tag tag) {
        try {
            Tag new_tag = tagService.createTag(tag);
            return Response.newSuccess(new_tag);
        } catch (IllegalArgumentException e) {
            return Response.newFail(e.getMessage());
        } catch (Exception e) {
            return Response.newFail("Failed to create tag: " + e.getMessage());
        }
    }

    @PutMapping("/Tag/{id}")
    public Response<Tag> updateTag(
            @PathVariable("id") long id,
            @RequestBody Tag tag) {
        try {
            tag.setId((int) id); // 确保更新的tag ID 是正确的
            Tag new_tag = tagService.updateTag(tag);
            return Response.newSuccess(new_tag);
        } catch (IllegalArgumentException e) {
            return Response.newFail(e.getMessage());
        } catch (Exception e) {
            return Response.newFail("Failed to update tag: " + e.getMessage());
        }
    }

    @DeleteMapping("/Tag/{id}")
    public Response<Long> deleteTag(@PathVariable long id) {
        try {
            tagService.deleteTagById(id);
            return Response.newSuccess(id);
        } catch (IllegalArgumentException e) {
            return Response.newFail(e.getMessage());
        } catch (Exception e) {
            return Response.newFail("Failed to delete tag: " + e.getMessage());
        }
    }

    @GetMapping("/User/{id}/Tags")
    public Response<Map<String, Object>> getUserTags(
            @PathVariable long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Map<String, Object> result = tagService.searchTagsByUserId(id, page, size);
            return Response.newSuccess(result);
        } catch (Exception e) {
            return Response.newFail("Failed to search tags of user " + id + ": " + e.getMessage());
        }
    }

    @GetMapping("/Tag/{id}/{userId}")
    public Response<Boolean> isTagAdministrator(@PathVariable long id, @PathVariable long userId) {
        try {
            return Response.newSuccess(tagService.isTagAdministrator(id, userId));
        } catch (Exception e) {
            return Response.newFail("Failed to check if tag administrator is user " + userId + ": " + e.getMessage());
        }
    }
}

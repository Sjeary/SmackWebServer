package org.example.smackwebserver.controller;

import org.example.smackwebserver.Response;
import org.example.smackwebserver.dao.*;
import org.example.smackwebserver.service.CommentService;
import org.example.smackwebserver.service.DynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class DynamicController {
    @Autowired
    private DynamicService dynamicService;
    @Autowired
    private CommentService<DynamicComment> dynamicCommentService;
    @Autowired
    private CommentService<SpotComment> spotCommentService;
    @Autowired
    private CommentService<ProductComment> productCommentService;

    @GetMapping("/Dynamic/{id}")
    public Response<Dynamic> getDynamic(@PathVariable long id) {
        try {
            Dynamic dynamic = dynamicService.getDynamicById(id);
            return Response.newSuccess(dynamic);
        } catch (IllegalArgumentException e) {
            // 捕获服务层抛出的异常并返回失败响应
            return Response.newFail(e.getMessage());
        }
    }

    @PostMapping("/Dynamic")
    public Response<Dynamic> createDynamic(@RequestBody Dynamic dynamic, @RequestParam List<String> tags) {
        try {
            Dynamic new_dynamic = dynamicService.createDynamic(dynamic, tags);
            return Response.newSuccess(new_dynamic);
        } catch (IllegalArgumentException e) {
            return Response.newFail(e.getMessage());
        } catch (Exception e) {
            return Response.newFail("Failed to create dynamic: " + e.getMessage());
        }
    }


    @PutMapping("/Dynamic/{id}")
    public Response<Dynamic> updateDynamic(
            @PathVariable("id") long id,
            @RequestBody Dynamic dynamic,
            @RequestParam List<String> tags) {
        try {
            dynamic.setId((int) id); // 确保更新的动态 ID 是正确的
            Dynamic new_dynamic = dynamicService.updateDynamic(dynamic, tags);
            return Response.newSuccess(new_dynamic);
        } catch (IllegalArgumentException e) {
            return Response.newFail(e.getMessage());
        } catch (Exception e) {
            return Response.newFail("Failed to update dynamic: " + e.getMessage());
        }
    }

    @DeleteMapping("/Dynamic/{id}")
    public Response<Long> deleteDynamic(@PathVariable long id) {
        try {
            dynamicService.deleteDynamicById(id);
            return Response.newSuccess(id);
        } catch (IllegalArgumentException e) {
            return Response.newFail(e.getMessage());
        } catch (Exception e) {
            return Response.newFail("Failed to delete dynamic: " + e.getMessage());
        }
    }

    @GetMapping("/User/{id}/Dynamics")
    public Response<Map<String, Object>> getUserDynamics(
            @PathVariable long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Map<String, Object> result = dynamicService.searchDynamicsByUserId(id, page, size);
            return Response.newSuccess(result);
        } catch (Exception e) {
            return Response.newFail("Failed to search travel products: " + e.getMessage());
        }
    }

    // 获取评论，一定要显式传入实体类！
    @GetMapping("/Dynamic/{id}/Comments")
    public Response<List<DynamicComment>> getDynamicComments(
            @PathVariable int id) {
        try {
            return Response.newSuccess(dynamicCommentService.getNestedComments(id, DynamicComment.class));
        } catch (IllegalArgumentException e) {
            return Response.newFail(e.getMessage());
        }
    }
    @GetMapping("/Spot/{id}/Comments")
    public Response<List<SpotComment>> getSpotComments(
            @PathVariable int id) {
        try {
            return Response.newSuccess(spotCommentService.getNestedComments(id, SpotComment.class));
        } catch (IllegalArgumentException e) {
            return Response.newFail(e.getMessage());
        }
    }
    @GetMapping("/Product/{id}/Comments")
    public Response<List<ProductComment>> getProductComments(
            @PathVariable int id) {
        try {
            return Response.newSuccess(productCommentService.getNestedComments(id, ProductComment.class));
        } catch (IllegalArgumentException e) {
            return Response.newFail(e.getMessage());
        }
    }
}

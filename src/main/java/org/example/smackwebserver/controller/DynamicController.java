package org.example.smackwebserver.controller;

import org.example.smackwebserver.Response;
import org.example.smackwebserver.dao.Dynamic;
import org.example.smackwebserver.service.DynamicService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class DynamicController {
    private final DynamicService dynamicService;

    public DynamicController(DynamicService dynamicService) {
        this.dynamicService = dynamicService;
    }

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
            // 捕获服务层抛出的异常并返回失败响应
            return Response.newFail(e.getMessage());
        } catch (Exception e) {
            // 捕获其他可能的异常
            return Response.newFail("Failed to create dynamic: " + e.getMessage());
        }
    }

    @PutMapping("/Dynamic/{id}")
    public Response<Dynamic> updateDynamic(
            @PathVariable("id") long id,
            @RequestBody Dynamic dynamic,
            @RequestParam List<String> tags) {
        try {
            dynamic.setId((int) id); // 确保更新的产品 ID 是正确的
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
}

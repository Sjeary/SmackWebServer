package org.example.smackwebserver.controller;

import org.example.smackwebserver.Response;
import org.example.smackwebserver.dao.Dynamic;
import org.example.smackwebserver.dao.ProductComment;
import org.example.smackwebserver.dao.Tag;
import org.example.smackwebserver.dao.TravelProduct;
import org.example.smackwebserver.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TravelProductController {
    @Autowired
    private TravelProductService travelProductService;
    @Autowired
    private CommentService<ProductComment> commentService;
    @Autowired
    private DynamicService dynamicService;
    @Autowired
    private SmackPubSubService pubSubService;
    @Autowired
    private UserService userService;

    @GetMapping("/api/v1/TravelProduct/{id}")
    public Response<TravelProduct> getTravelProduct(@PathVariable("id") long id) {
        try {
            TravelProduct product = travelProductService.getTravelProductById(id);
            return Response.newSuccess(product);
        } catch (IllegalArgumentException e) {
            // 捕获服务层抛出的异常并返回失败响应
            return Response.newFail(e.getMessage());
        }
    }

    @PostMapping("/api/v1/TravelProduct")
    public Response<Long> createTravelProduct(@RequestBody TravelProduct travelProduct) {
        try {
            Long productId = travelProductService.createTravelProduct(travelProduct);
            TravelProduct product = travelProductService.getTravelProductById(productId);
            Dynamic dynamic = dynamicService.createDynamic(product, "发布旅游产品：");
            pubSubService.afterPropertiesSet();
            for (Tag tag : dynamic.getTags()) {
                String message = String.format("订阅的 %s 有新动态发布：%s", tag.getName(), dynamic.getTitle());
                pubSubService.publishMessageToTagNode(tag.getName(), message);
            }
            String message = String.format("关注的 %s 有新动态发布：%s", userService.getUserById(dynamic.getUserId()).getName(), dynamic.getTitle());
            pubSubService.publishMessageToUserNode(dynamic.getUserId(), message);
            return Response.newSuccess(productId);
        } catch (IllegalArgumentException e) {
            // 捕获服务层抛出的异常并返回失败响应
            return Response.newFail(e.getMessage());
        } catch (Exception e) {
            // 捕获其他可能的异常
            return Response.newFail("Failed to create travel product: " + e.getMessage());
        }
    }
    @PutMapping("/api/v1/TravelProduct/{id}")
    public Response<Long> updateTravelProduct(
            @PathVariable("id") long id,
            @RequestBody TravelProduct travelProduct) {
        try {
            travelProduct.setId((int) id); // 确保更新的产品 ID 是正确的
            Long updatedId = travelProductService.updateTravelProduct(travelProduct);
            TravelProduct product = travelProductService.getTravelProductById(updatedId);
            Dynamic dynamic = dynamicService.createDynamic(product, "更新旅游产品：");
            pubSubService.afterPropertiesSet();
            for (Tag tag : dynamic.getTags()) {
                String message = String.format("订阅的 %s 有新动态更新：%s", tag.getName(), dynamic.getTitle());
                pubSubService.publishMessageToTagNode(tag.getName(), message);
            }
            String message = String.format("关注的 %s 有新动态更新：%s", userService.getUserById(dynamic.getUserId()).getName(), dynamic.getTitle());
            pubSubService.publishMessageToUserNode(dynamic.getUserId(), message);
            return Response.newSuccess(updatedId);
        } catch (IllegalArgumentException e) {
            return Response.newFail(e.getMessage());
        } catch (Exception e) {
            return Response.newFail("Failed to update travel product: " + e.getMessage());
        }
    }

    @DeleteMapping("/api/v1/TravelProduct/{id}")
    public Response<Long> deleteTravelProduct(@PathVariable("id") long id) {
        try {
            travelProductService.deleteTravelProductById(id);
            return Response.newSuccess(id);
        } catch (IllegalArgumentException e) {
            return Response.newFail(e.getMessage());
        } catch (Exception e) {
            return Response.newFail("Failed to delete travel product: " + e.getMessage());
        }
    }

    @GetMapping("/api/v1/TravelProduct/search")
    public Response<Map<String, Object>> searchTravelProducts(
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) String productType,
            @RequestParam(required = false) String theme,
            @RequestParam(required = false) String departureLocation,
            @RequestParam(required = false) String destination,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Map<String, Object> result = travelProductService.searchTravelProducts(
                    userId, productType, theme, departureLocation, destination, page, size);
            return Response.newSuccess(result);
        } catch (Exception e) {
            return Response.newFail("Failed to search travel products: " + e.getMessage());
        }
    }

    @GetMapping("/api/v1/TravelProduct/searchByKeyword")
    public Response<Page<TravelProduct>> searchProducts(
            @RequestParam("keyword") String keyword,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
            Page<TravelProduct> products = travelProductService.searchProductsByKeyword(keyword, pageable);
            return Response.newSuccess(products);
        } catch (Exception e) {
            return Response.newFail("Failed to search travel products: " + e.getMessage());
        }
    }

    // 获取评论，一定要显式传入实体类！
    @GetMapping("/api/v1/TravelProduct/{id}/Comments")
    public Response<List<ProductComment>> getComments(
            @PathVariable int id) {
        try {
            return Response.newSuccess(commentService.getNestedComments(id, ProductComment.class));
        } catch (IllegalArgumentException e) {
            return Response.newFail(e.getMessage());
        }
    }

    @PostMapping("/api/v1/TravelProduct/{id}/Comments")
    public Response<ProductComment> createComment(@RequestBody ProductComment productComment, @PathVariable("id") int id) {
        try {
            TravelProduct travelProduct = travelProductService.getTravelProductById(id);
            if (travelProduct == null) {
                return Response.newFail("Travel Product not found");
            }
            ProductComment new_productComment = commentService.createComment(id, productComment, ProductComment.class);
            return Response.newSuccess(new_productComment);
        } catch (IllegalArgumentException e) {
            // 捕获服务层抛出的异常并返回失败响应
            return Response.newFail(e.getMessage());
        } catch (Exception e) {
            // 捕获其他可能的异常
            return Response.newFail("Failed to create travel product comment: " + e.getMessage());
        }
    }
}

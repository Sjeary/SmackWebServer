package org.example.smackwebserver.controller;

import org.example.smackwebserver.Response;
import org.example.smackwebserver.dao.*;
import org.example.smackwebserver.service.CommentService;
import org.example.smackwebserver.service.DynamicService;
import org.example.smackwebserver.service.SmackPubSubService;
import org.example.smackwebserver.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class DynamicController {
    private final DynamicService dynamicService;
    private final CommentService<DynamicComment> commentService;
    private final SmackPubSubService pubSubService;
    private final UserService userService;

    public DynamicController(DynamicService dynamicService, CommentService<DynamicComment> commentService, SmackPubSubService pubSubService, UserService userService) {
        this.dynamicService = dynamicService;
        this.commentService = commentService;
        this.pubSubService = pubSubService;
        this.userService = userService;
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
            pubSubService.afterPropertiesSet();
            for (Tag tag : new_dynamic.getTags()) {
                String message = String.format("订阅的 %s 有新动态发布：%s", tag.getName(), new_dynamic.getTitle());
                pubSubService.publishMessageToTagNode(tag.getName(), message);
            }
            String message = String.format("关注的 %s 有新动态发布：%s", userService.getUserById(new_dynamic.getUserId()).getName(), new_dynamic.getTitle());
            pubSubService.publishMessageToUserNode(dynamic.getUserId(), message);
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
            pubSubService.afterPropertiesSet();
            for (Tag tag : new_dynamic.getTags()) {
                String message = String.format("订阅的 %s 有新动态更新：%s", tag.getName(), new_dynamic.getTitle());
                pubSubService.publishMessageToTagNode(tag.getName(), message);
            }
            String message = String.format("关注的 %s 有新动态更新：%s", userService.getUserById(new_dynamic.getUserId()).getName(), new_dynamic.getTitle());
            pubSubService.publishMessageToUserNode(dynamic.getUserId(), message);
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
            commentService.deleteComments((int) id, DynamicComment.class);
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
            return Response.newFail("Failed to get dynamics of user " + id + ": " + e.getMessage());
        }
    }

    // 获取评论，一定要显式传入实体类！
    @GetMapping("/Dynamic/{id}/Comments")
    public Response<List<DynamicComment>> getDynamicComments(
            @PathVariable int id) {
        try {
            return Response.newSuccess(commentService.getNestedComments(id, DynamicComment.class));
        } catch (IllegalArgumentException e) {
            return Response.newFail(e.getMessage());
        }
    }

    @PostMapping("/Dynamic/{id}/Comments")
    public Response<DynamicComment> createComment(@RequestBody DynamicComment dynamicComment, @PathVariable("id") int id) {
        try {
            Dynamic dynamic = dynamicService.getDynamicById(id);
            if (dynamic == null) {
                return Response.newFail("Dynamic not found");
            }
            DynamicComment new_dynamicComment = commentService.createComment(id, dynamicComment, DynamicComment.class);
            return Response.newSuccess(new_dynamicComment);
        } catch (IllegalArgumentException e) {
            // 捕获服务层抛出的异常并返回失败响应
            return Response.newFail(e.getMessage());
        } catch (Exception e) {
            // 捕获其他可能的异常
            return Response.newFail("Failed to create dynamic comment: " + e.getMessage());
        }
    }
}

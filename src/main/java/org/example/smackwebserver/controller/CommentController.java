package org.example.smackwebserver.controller;

import org.example.smackwebserver.Response;
import org.example.smackwebserver.dao.Comment;
import org.example.smackwebserver.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{commentType}/{id}/comments")
    public Response<List<? extends Comment>> getNestedComments(
            @PathVariable String commentType,
            @PathVariable int id) {
        try {
            return Response.newSuccess(commentService.getNestedComments(commentType, id));
        } catch (IllegalArgumentException e) {
            return Response.newFail(e.getMessage());
        }
    }
}


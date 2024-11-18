package org.example.smackwebserver.service;

import org.example.smackwebserver.dao.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final ProductCommentRepository productCommentRepository;
    private final SpotCommentRepository spotCommentRepository;
    private final DynamicCommentRepository dynamicCommentRepository;

    public CommentServiceImpl(ProductCommentRepository productCommentRepository,
                              SpotCommentRepository spotCommentRepository, DynamicCommentRepository dynamicCommentRepository) {
        this.productCommentRepository = productCommentRepository;
        this.spotCommentRepository = spotCommentRepository;
        this.dynamicCommentRepository = dynamicCommentRepository;
    }

    @Override
    public List<? extends Comment> getNestedComments(String parentType, int parentId) {
        List<? extends Comment> topLevelComments = getRepository(parentType).findTopByParentId(parentId);
        for (Comment comment : topLevelComments) {
            comment.setReplies(getRepository(parentType).findByReplyId(comment.getId()));
        }
        return topLevelComments;
    }

    private CommentRepository<? extends Comment> getRepository(String parentType) {
        return switch (parentType) {
            case "TravelProduct" -> productCommentRepository;
            case "Spot" -> spotCommentRepository;
            case "Dynamic" -> dynamicCommentRepository;
            default -> throw new IllegalArgumentException("Unknown comment parent type: " + parentType);
        };
    }
}

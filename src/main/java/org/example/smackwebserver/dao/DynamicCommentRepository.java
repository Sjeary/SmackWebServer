package org.example.smackwebserver.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface DynamicCommentRepository extends CommentRepository<SpotComment> {
}

package org.example.smackwebserver.service;

import org.example.smackwebserver.dao.Tag;

import java.util.Map;

public interface TagService {
    Map<String, Object> getAllTags(int page, int size);

    Map<String, Object> searchTag(String keyword, int page, int size);

    Tag getTagById(long id);
}

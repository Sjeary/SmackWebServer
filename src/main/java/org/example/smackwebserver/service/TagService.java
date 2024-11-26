package org.example.smackwebserver.service;

import org.example.smackwebserver.dao.Tag;

import java.util.List;
import java.util.Map;

public interface TagService {
    Map<String, Object> getAllTags(int page, int size);

    List<String> getTagList();

    Map<String, Object> searchTag(String keyword, int page, int size);

    Tag getTagById(long id);

    Map<String, Object> searchTagsByUserId(long userId, int page, int size);

    Tag createTag(Tag tag);

    Tag updateTag(Tag tag);

    void deleteTagById(long id);

    boolean isTagAdministrator(long id, long userId);
}

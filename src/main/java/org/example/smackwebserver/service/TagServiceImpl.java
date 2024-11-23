package org.example.smackwebserver.service;

import org.example.smackwebserver.dao.Tag;
import org.example.smackwebserver.dao.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Map<String, Object> getAllTags(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Tag> productPage = tagRepository.findAll(pageable);

        // 将数据与总记录数封装为Map返回
        Map<String, Object> result = new HashMap<>();
        result.put("total_item", productPage.getTotalElements()); // 总记录数
        result.put("data", productPage.getContent()); // 当前页的数据

        return result;
    }

    @Override
    public Map<String, Object> searchTag(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Tag> productPage = tagRepository.findByNameContaining(keyword, pageable);

        // 将数据与总记录数封装为Map返回
        Map<String, Object> result = new HashMap<>();
        result.put("total_item", productPage.getTotalElements()); // 总记录数
        result.put("data", productPage.getContent()); // 当前页的数据

        return result;
    }

    @Override
    public Tag getTagById(long id) {
        return tagRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Tag with id " + id + " not found"));
    }

}

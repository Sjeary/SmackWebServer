package org.example.smackwebserver.service;

import org.example.smackwebserver.dao.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class DynamicServiceImpl implements DynamicService {
    private final DynamicRepository dynamicRepository;
    private final TagRepository tagRepository;

    public DynamicServiceImpl(DynamicRepository dynamicRepository, TagRepository tagRepository) {
        this.dynamicRepository = dynamicRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public Dynamic getDynamicById(long id) {
        return dynamicRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Dynamic not found with id: " + id));
    }

    @Override
    public Dynamic createDynamic(Dynamic dynamic, List<String> tagNames) {
        // 校验规则

        // 1. 动态标题
        if (dynamic.getTitle() == null || dynamic.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Dynamic title cannot be empty");
        }
        if (dynamic.getTitle().length() > 100) {
            throw new IllegalArgumentException("Dynamic title length cannot exceed 100 characters");
        }

        // 2. 动态内容
        if (dynamic.getContent() == null || dynamic.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Dynamic content cannot be empty");
        }
        if (dynamic.getContent().length() > 255) {
            throw new IllegalArgumentException("Dynamic content length cannot exceed 255 characters");
        }

        // 保存到数据库
        dynamic.setIssuedAt(LocalDateTime.now());
        dynamic.setUpdatedAt(LocalDateTime.now());

        for (String tagName : tagNames) {
            Tag tag = tagRepository.findByName(tagName);
            if (tag == null) {
                tag = new Tag();
                tag.setName(tagName);
                tagRepository.save(tag);
            }
            dynamic.getTags().add(tag);
        }

        return dynamicRepository.save(dynamic);
    }

    @Override
    public Dynamic updateDynamic(Dynamic dynamic, List<String> tagNames) {
        // 检查是否存在指定ID的动态
        Dynamic existingProduct = dynamicRepository.findById((long) dynamic.getId())
                .orElseThrow(() -> new IllegalArgumentException("Dynamic with ID " + dynamic.getId() + " does not exist"));

        // 更新非空字段
        if (dynamic.getTitle() != null) {
            existingProduct.setTitle(dynamic.getTitle());
        }
        if (dynamic.getContent() != null) {
            existingProduct.setContent(dynamic.getContent());
        }
        if (dynamic.getUrlId() != null) {
            existingProduct.setUrlId(dynamic.getUrlId());
        }

        // 更新更新时间
        existingProduct.setUpdatedAt(LocalDateTime.now());

        // 更新标签
        existingProduct.getTags().clear(); // 清除旧的标签
        for (String tagName : tagNames) {
            Tag tag = tagRepository.findByName(tagName);
            if (tag == null) {
                tag = new Tag();
                tag.setName(tagName);
                tagRepository.save(tag);
            }
            existingProduct.getTags().add(tag);
        }

        // 保存修改后的对象
        return dynamicRepository.save(existingProduct);
    }


    @Override
    public void deleteDynamicById(long id) {
        if (!dynamicRepository.existsById(id)) {
            throw new IllegalArgumentException("Travel product with ID " + id + " does not exist");
        }

        // 删除动态
        dynamicRepository.deleteById(id);
    }

    @Override
    public Map<String, Object> searchDynamicsByUserId(long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("issuedAt").descending());
        Page<Dynamic> productPage = dynamicRepository.findByUserId(userId, pageable);

        // 将数据与总记录数封装为Map返回
        Map<String, Object> result = new HashMap<>();
        result.put("total_item", productPage.getTotalElements()); // 总记录数
        result.put("data", productPage.getContent()); // 当前页的数据

        return result;
    }

    @Override
    public Map<String, Object> searchDynamicsByTagId(int tagId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("issuedAt").descending());
        Page<Dynamic> productPage = dynamicRepository.findByTagId(tagId, pageable);

        // 将数据与总记录数封装为Map返回
        Map<String, Object> result = new HashMap<>();
        result.put("total_item", productPage.getTotalElements()); // 总记录数
        result.put("data", productPage.getContent()); // 当前页的数据

        return result;
    }
}

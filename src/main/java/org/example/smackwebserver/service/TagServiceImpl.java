package org.example.smackwebserver.service;

import org.example.smackwebserver.dao.Tag;
import org.example.smackwebserver.dao.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<String> getTagList() {
        List<Tag> tags = tagRepository.findAll();

        List<String> tagList = new ArrayList<>();
        for (Tag tag : tags) {
            tagList.add(tag.getName());
        }

        return tagList;
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

    @Override
    public Map<String, Object> searchTagsByUserId(long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Tag> productPage = tagRepository.findByUserId(userId, pageable);

        // 将数据与总记录数封装为Map返回
        Map<String, Object> result = new HashMap<>();
        result.put("total_item", productPage.getTotalElements()); // 总记录数
        result.put("data", productPage.getContent()); // 当前页的数据

        return result;
    }

    @Override
    public Tag createTag(Tag tag) {
        // 检查非空字段
        if (tag.getName() == null || tag.getName().isEmpty())
            throw new IllegalArgumentException("Tag name is empty.");
        if (tag.getDescription() == null || tag.getDescription().isEmpty())
            throw new IllegalArgumentException("Tag description is empty.");

        // 检查重复性
        Tag existingTag = tagRepository.findByName(tag.getName());
        if (existingTag != null)
            throw new IllegalArgumentException("Tag with name " + tag.getName() + " already exists");

        return tagRepository.save(tag);
    }

    @Override
    public Tag updateTag(Tag tag) {
        // 检查Tag是否存在
        Tag existingTag = getTagById(tag.getId());

        // 更新非空字段
        if (tag.getName() != null) {
            // 检查重复性
            if (!existingTag.getName().equals(tag.getName())){
                Tag tmpTag = tagRepository.findByName(tag.getName());
                if (tmpTag != null)
                    throw new IllegalArgumentException("Tag with name " + tag.getName() + " already exists");
            }
            existingTag.setName(tag.getName());
        }
        if (tag.getDescription() != null) {
            existingTag.setDescription(tag.getDescription());
        }

        return tagRepository.save(existingTag);
    }

    @Override
    public void deleteTagById(long id) {
        // 检查Tag是否存在
        if (!tagRepository.existsById(id)) {
            throw new IllegalArgumentException("Tag with ID " + id + " does not exist");
        }

        // 删除Tag
        tagRepository.deleteById(id);;


    }

    @Override
    public boolean isTagAdministrator(long id, long userId) {
        Tag tag = getTagById(id);
        return tag.getUserId() == userId;
    }
}

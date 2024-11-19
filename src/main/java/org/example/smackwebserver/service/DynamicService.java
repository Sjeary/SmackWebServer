package org.example.smackwebserver.service;

import org.example.smackwebserver.dao.Dynamic;

import java.util.List;
import java.util.Map;

public interface DynamicService {
    Dynamic getDynamicById(long id);

    Dynamic createDynamic(Dynamic dynamic, List<String> tagNames);

    Dynamic updateDynamic(Dynamic dynamic, List<String> tagNames);

    void deleteDynamicById(long id);

    Map<String, Object> searchDynamicsByUserId(long userId, int page, int size);

    Map<String, Object> searchDynamicsByTagId(int tagId, int page, int size);
}

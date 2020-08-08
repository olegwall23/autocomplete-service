package com.example.autocompletenodeservice.service.impl;

import com.example.autocompletenodeservice.service.CachingService;
import com.example.autocompletenodeservice.vo.AutocompleteCache;
import com.example.autocompletenodeservice.vo.WordRank;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CachingServiceImplBase implements CachingService {

    private Map<String, AutocompleteCache> cache = new HashMap<>();

    @Override
    public void add(String query, List<WordRank> wordRankList) {
        cache.put(query, AutocompleteCache.builder()
                .createdTime(System.currentTimeMillis())
                .wordRankList(wordRankList)
                .build());
    }

    @Override
    public List<WordRank> get(String query) {
        AutocompleteCache autocompleteCache = cache.get(query);
        if (autocompleteCache == null) {
            return null;
        }
        return autocompleteCache.getWordRankList();
    }


}

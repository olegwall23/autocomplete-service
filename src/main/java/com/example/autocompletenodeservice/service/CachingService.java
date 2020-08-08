package com.example.autocompletenodeservice.service;

import com.example.autocompletenodeservice.vo.WordRank;

import java.util.List;

public interface CachingService {

    void add(String query, List<WordRank> wordRankList);

    List<WordRank> get(String query);

}

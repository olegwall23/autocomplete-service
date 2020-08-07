package com.example.autocompletenodeservice.service;

import com.example.autocompletenodeservice.vo.WordRank;

import java.util.List;

public interface AutocompleteService {

    void add(String word, boolean incrementRequests);

    boolean wordExists(String word);

    int getWordRank(String word);

    List<WordRank> getRankForAllWords();

    List<WordRank> getTopSuggestedWords(String prefix, int wordsToShow);

}

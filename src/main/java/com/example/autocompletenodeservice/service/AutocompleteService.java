package com.example.autocompletenodeservice.service;

import com.example.autocompletenodeservice.vo.WordRank;

import java.util.List;

public interface AutocompleteService {

    void add(String word, boolean incrementRequests);

    void add(String word, int rank);

    boolean wordExists(String word);

    int getWordRank(String word);

    List<WordRank> getRankForAllWords();

    List<WordRank> getTopSuggestedWords(String prefix, int wordsToShow);

    List<WordRank> getTopSuggestedWordsWithCache(String prefix, int wordsToShow);

}

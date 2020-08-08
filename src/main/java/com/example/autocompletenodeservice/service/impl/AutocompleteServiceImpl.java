package com.example.autocompletenodeservice.service.impl;

import com.example.autocompletenodeservice.service.AutocompleteService;
import com.example.autocompletenodeservice.service.CachingService;
import com.example.autocompletenodeservice.vo.Node;
import com.example.autocompletenodeservice.vo.WordRank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class AutocompleteServiceImpl implements AutocompleteService {

    @Autowired
    private CachingService cachingService;

    private static Node node = new Node();

    @Override
    public void add(String word, boolean incrementRequests) {
        add(word, true, null);
    }

    @Override
    public void add(String word, int rank) {
        add(word, false, rank);
    }

    private void add(String word, boolean increment, Integer rank) {
        synchronized (node) {
            Node startFrom = node;
            for (int i = 0; i < word.length(); i++) {
                String character = String.valueOf(word.charAt(i));
                if (startFrom.getChildren().containsKey(character)) {
                    startFrom = startFrom.getChildren().get(character);
                } else {
                    Node newNode = new Node();
                    startFrom.getChildren().put(character, newNode);
                    startFrom = newNode;
                }
            }
            startFrom.setWord(word);
            if (increment) {
                startFrom.setCount(startFrom.getCount() + 1);
            } else if (rank != null) {
                startFrom.setCount(rank);
            }
        }
    }

    @Override
    public boolean wordExists(String word) {
        synchronized (node) {
            Node startFrom = node;
            for (int i = 0; i < word.length(); i++) {
                String c = Character.toString(word.charAt(i));
                Node n = startFrom.getChildren().get(c);

                if (n == null) {
                    return false;
                }

                if (i == word.length() - 1) {
                    return word.equals(n.getWord());
                }

                startFrom = n;
            }
        }

        return false;
    }

    @Override
    public int getWordRank(String word) {
        synchronized (node) {
            Node startFrom = node;
            for (int i = 0; i < word.length(); i++) {
                String c = Character.toString(word.charAt(i));
                Node n = startFrom.getChildren().get(c);

                if (n == null) {
                    return -1;
                }

                if (i == word.length() - 1 && word.equals(n.getWord())) {
                    return n.getCount();
                }

                startFrom = n;
            }
        }

        return -1;
    }

    @Override
    public List<WordRank> getRankForAllWords() {
        synchronized (node) {
            List<WordRank> wordRanks = new ArrayList<>();
            getWordRankRec(node, wordRanks);
            System.out.println();

            Collections.sort(wordRanks);

            wordRanks.forEach(wordRank -> {
                System.out.println(wordRank.getWord() + " | " + wordRank.getRank());
            });
        }

        return null;
    }

    private Node goToNodeByPrefix(Node node, String prefix) {
        for (int i = 0; i < prefix.length(); i++) {
            String c = Character.toString(prefix.charAt(i));
            node = node.getChildren().get(c);
        }
        return node;
    }

    @Override
    public List<WordRank> getTopSuggestedWords(String prefix, int wordsToShow) {
        synchronized (node) {
            List<WordRank> wordRanks = new ArrayList<>();

            Node startFrom = goToNodeByPrefix(node, prefix);
            getWordRankRec(startFrom, wordRanks);

            Collections.sort(wordRanks);

            return wordRanks.subList(0, Math.min(wordsToShow, wordRanks.size()));
        }
    }

    @Override
    public List<WordRank> getTopSuggestedWordsWithCache(String prefix, int wordsToShow) {
        List<WordRank> result = cachingService.get(prefix);
        if (result != null) {
            return result;
        }

        result = getTopSuggestedWords(prefix, wordsToShow);
        cachingService.add(prefix, result);

        return result;
    }

    private void getWordRankRec(Node node, List<WordRank> wordRanks) {
        if (node.getWord() != null) {
            wordRanks.add(new WordRank(node.getWord(), node.getCount()));
        }

        for (Map.Entry<String, Node> n : node.getChildren().entrySet()) {
            getWordRankRec(n.getValue(), wordRanks);
        }
    }

}

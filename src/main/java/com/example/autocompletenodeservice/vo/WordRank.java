package com.example.autocompletenodeservice.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WordRank implements Comparable<WordRank> {

    private String word;

    private int rank;

    @Override
    public int compareTo(WordRank o) {
        return o.getRank() - this.rank;
    }
}

package com.example.autocompletenodeservice.vo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AutocompleteCache {

    private Long createdTime;

    private List<WordRank> wordRankList;

}

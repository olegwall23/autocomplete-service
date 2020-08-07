package com.example.autocompletenodeservice.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Node {

    private Map<String, Node> children = new HashMap<>();

    private List<String> topWordsAutocomplete = new ArrayList<>();

    private String word;

    private int count = 0;

}

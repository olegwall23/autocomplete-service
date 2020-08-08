package com.example.autocompletenodeservice;

import com.example.autocompletenodeservice.service.AutocompleteService;
import com.example.autocompletenodeservice.service.CachingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AutocompleteNodeServiceApplicationTests {

	@Autowired
	private AutocompleteService autocompleteService;

	@Autowired
	private CachingService cachingService;

	@Test
	void contextLoads() throws IOException {
		File fileWithWords = new File("/home/oleh/IdeaProjects/autocomplete/autocomplete-service/autocomplete-node-service/src/main/resources/words.txt");

		FileInputStream fstream = new FileInputStream(fileWithWords);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		Set<String> words = new HashSet<>();

		String strLine;
		while ((strLine = br.readLine()) != null)   {
			words.add(strLine.toLowerCase().trim());
		}
		fstream.close();

		words.forEach(word -> {
			autocompleteService.add(word, new Random().nextInt(100));
		});

		autocompleteService.getTopSuggestedWordsWithCache("h", 25).forEach(wordRank -> {
			System.out.println(wordRank.getWord() + " | " + wordRank.getRank());
		});;

		System.out.println("--------------------- FROM CACHE ------------");

		autocompleteService.getTopSuggestedWordsWithCache("h", 25).forEach(wordRank -> {
			System.out.println(wordRank.getWord() + " | " + wordRank.getRank());
		});;
	}

}
